package com.victoriaKlein.UserSignUpSignIn.securityAuth.authenticationHandler;

import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.securityAuth.AuthUserDetails;
import com.victoriaKlein.UserSignUpSignIn.securityAuth.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private AuthUserService authUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
        String userName = request.getParameter("userName");
        User user = authUserService.findUserByName(userName);
        if (user.getFailedAttempt() > 0) {
            authUserService.resetFailedAttempts(user.getName());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }

}