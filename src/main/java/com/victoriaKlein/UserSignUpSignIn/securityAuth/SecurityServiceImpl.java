package com.victoriaKlein.UserSignUpSignIn.securityAuth;

import com.victoriaKlein.UserSignUpSignIn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private final UserService userService;

    public SecurityServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void autoLogIn(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password);
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }
}
