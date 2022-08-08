package com.victoriaKlein.UserSignUpSignIn.securityAuth.authenticationHandler;

import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.securityAuth.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private AuthUserService authUserService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String userName = request.getParameter("userName");
        System.out.println("USERNAME  " + userName);
        User user = authUserService.findUserByName(userName);
        System.out.println(user.getName());

        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < AuthUserService.MAX_FAILED_ATTEMPTS - 1) {
                    authUserService.increaseFailedAttempts(user);
                } else {
                    authUserService.lock(user);
                    exception = new LockedException("Your account has been locked due to 10 failed attempts."
                            + " It will be unlocked after 1 hour.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (authUserService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }

        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }

}