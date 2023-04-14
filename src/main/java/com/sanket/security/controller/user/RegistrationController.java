package com.sanket.security.controller.user;

import com.sanket.security.config.TokenGenerator;
import com.sanket.security.dto.GenericResponseDTO;
import com.sanket.security.dto.error.ErrorDetails;
import com.sanket.security.dto.user.*;
import com.sanket.security.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenGenerator tokenGenerator;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> add(@RequestBody @Valid UserDTO userDTO){
        return new ResponseEntity<>(userService.add(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<GenericResponseDTO> confirmAccount(@RequestParam("token")String confirmationToken){
        final String message = userService.verifyUserAccount(confirmationToken);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.OK);
    }

    @GetMapping("/verification-token/{email}")
    public ResponseEntity<GenericResponseDTO> resendVerificationToken(@PathVariable("email") String email){
        final String message = userService.resendVerificationEmail(email);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<GenericResponseDTO> getResetPasswordToken(@PathVariable("email") String email){
        final String message = userService.getResetPasswordToken(email);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.OK);
    }

    @PostMapping("/reset-password/token/{token}")
    public ResponseEntity<GenericResponseDTO> resetPassword(@PathVariable("token") String token,
                                                @RequestBody ResetPasswordDTO resetPasswordDTO){
        final String message = userService.resetPassword(token, resetPasswordDTO);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.ACCEPTED);
    }

    @GetMapping("/forget-password/{email}")
    public ResponseEntity<GenericResponseDTO> getForgetPasswordToken(@PathVariable("email") String email){
        final String message = userService.getForgetPasswordToken(email);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.OK);
    }

    @PostMapping("/forget-password/token/{token}")
    public ResponseEntity<GenericResponseDTO> changePassword(@PathVariable("token") String token,
                                                            @RequestBody ChangePassword changePassword){
        final String message = userService.changePassword(token, changePassword);
        return new ResponseEntity<>(new GenericResponseDTO(message), HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getByEmail(@RequestBody LoginDTO loginDTO){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            //if(ex instanceof BadCredentialsException || ex instanceof Account)
            ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), "401");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }
        String jwtToken = tokenGenerator.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(jwtToken));
    }
}
