package com.sanket.security.config;

import com.sanket.security.dao.UserRepository;
import com.sanket.security.model.User;
import com.sanket.security.service.user.exception.UserAccountNotVerifiedException;
import com.sanket.security.service.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        final User user = verifyUserAccount(username);
        return new CustomUserDetails(user);
    }

    public User verifyUserAccount(final String email) {
        final User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(email));
        if(!user.getIsEnabled()) throw new UserAccountNotVerifiedException();
        return user;
    }
}
