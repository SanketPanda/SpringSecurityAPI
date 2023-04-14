package com.sanket.security.service.user;

import com.sanket.security.dao.ConfirmationTokenRepository;
import com.sanket.security.dto.user.ChangePassword;
import com.sanket.security.dto.user.ResetPasswordDTO;
import com.sanket.security.dto.user.UserDTO;
import com.sanket.security.dto.user.UsersDTO;
import com.sanket.security.exception.GenericException;
import com.sanket.security.model.ConfirmationToken;
import com.sanket.security.service.email.EmailService;
import com.sanket.security.service.user.exception.*;
import com.sanket.security.model.User;
import com.sanket.security.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id - "+userId));
    }

    public UserDTO getUser(final String email) {
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return UserMapper.INSTANCE.toDTO(user);
    }

    public List<UsersDTO> getAllUsers() {
        List<UserDTO> userDTOS = userRepository.findAll().stream()
                .filter(User::getIsEnabled)
                .map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return userDTOS.stream().map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO add(UserDTO userDTO) {
        Optional<User> theUser = userRepository.findByEmail(userDTO.getEmail());
        if (theUser.isPresent()){
            throw new UserAlreadyExistsException(userDTO.getEmail());
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        final User user = UserMapper.INSTANCE.toEntity(userDTO);
        user.setIsEnabled(false);
        final User newUser = userRepository.save(user);
        emailService.sendAccountVerificationEmail(newUser);
        return UserMapper.INSTANCE.toDTO(newUser);
    }

    @Transactional
    public void update(UsersDTO userDTO) {
        final User user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with id: ", userDTO.getUserId()));
        final String currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!currentAuthenticatedUser.equals(user.getEmail())) throw new UnAuthorizedAction("You are not authorized to update details of user: "+user.getEmail());
        Optional.ofNullable(userDTO.getUserId()).orElseThrow(UserIdRequiredException::new);
        UserMapper.toEntity(userDTO, user);
    }

    @Transactional
    public void delete(String email) {
        final String currentAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!currentAuthenticatedUser.equals(email)) throw new UnAuthorizedAction("You are not authorized to delete user: "+email);
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public String verifyUserAccount(final String confirmationToken){
        final ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(confirmationToken).orElseThrow(InvalidConfirmationTokenException::new);
        User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UserNotFoundException(token.getUser().getEmail()));
        if(new Date().after(token.getExpirationDate())) throw new TokenExpiredException();
        user.setIsEnabled(true);
        confirmationTokenRepo.delete(token);
        return "Verification successful, account activated.";
    }

    @Transactional
    public String resendVerificationEmail(final String email){
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if(user.getIsEnabled()) throw new GenericException("User account already verified");
        final Optional<ConfirmationToken> expiredToken = confirmationTokenRepo.findByUserId(user.getUserId());
        expiredToken.ifPresent(confirmationToken -> confirmationTokenRepo.delete(confirmationToken));
        emailService.sendAccountVerificationEmail(user);
        return "Verification email has been sent to your registered email.";
    }

    @Transactional
    public String getResetPasswordToken(final String email){
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if(!user.getIsEnabled()) throw new UserAccountNotVerifiedException();
        final Optional<ConfirmationToken> expiredToken = confirmationTokenRepo.findByUserId(user.getUserId());
        expiredToken.ifPresent(confirmationToken -> confirmationTokenRepo.delete(confirmationToken));
        emailService.sendPasswordResetEmail(user);
        return "A link has been sent to your registered email, please use the link to reset your password";
    }

    @Transactional
    public String resetPassword(final String resetToken, final ResetPasswordDTO resetPasswordDTO){
        final ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(resetToken).orElseThrow(InvalidConfirmationTokenException::new);
        User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UserNotFoundException(token.getUser().getEmail()));
        if(!user.getIsEnabled()) throw new UserAccountNotVerifiedException();
        if(new Date().after(token.getExpirationDate())) throw new TokenExpiredException();
        if(!passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())) throw new BadCredentialsException("Old password you entered is incorrect.");
        if(resetPasswordDTO.getOldPassword().equals(resetPasswordDTO.getNewPassword())) throw new GenericException("Old password and new password can not be same.");
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        userRepository.save(user);
        confirmationTokenRepo.delete(token);
        return "Password changed successfully.";
    }

    @Transactional
    public String getForgetPasswordToken(final String email){
        final User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        if(!user.getIsEnabled()) throw new UserAccountNotVerifiedException();
        final Optional<ConfirmationToken> expiredToken = confirmationTokenRepo.findByUserId(user.getUserId());
        expiredToken.ifPresent(confirmationToken -> confirmationTokenRepo.delete(confirmationToken));
        emailService.sendForgetPasswordResetEmail(user);
        return "A link has been sent to your registered email, please use the link to reset your password";
    }

    @Transactional
    public String changePassword(final String resetToken, final ChangePassword changePassword){
        final ConfirmationToken token = confirmationTokenRepo.findByConfirmationToken(resetToken).orElseThrow(InvalidConfirmationTokenException::new);
        User user = userRepository.findByEmail(token.getUser().getEmail()).orElseThrow(() -> new UserNotFoundException(token.getUser().getEmail()));
        if(!user.getIsEnabled()) throw new UserAccountNotVerifiedException();
        if(new Date().after(token.getExpirationDate())) throw new TokenExpiredException();
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);
        confirmationTokenRepo.delete(token);
        return "Password changed successfully.";
    }
}