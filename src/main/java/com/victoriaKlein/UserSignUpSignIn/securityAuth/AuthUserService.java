package com.victoriaKlein.UserSignUpSignIn.securityAuth;
import com.victoriaKlein.UserSignUpSignIn.exception.NoUserFoundException;
import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class AuthUserService {
    public static final int MAX_FAILED_ATTEMPTS = 10;

    private static final long LOCK_TIME_DURATION = 60 * 60 * 1000;

    @Autowired
    private UserRepo userRepo;

    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepo.updateFailedAttempts(newFailAttempts, user.getPassword());
    }

    public void resetFailedAttempts(String password) {
        userRepo.updateFailedAttempts(0, password);
    }


    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepo.save(user);
    }

    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            userRepo.save(user);

            return true;
        }

        return false;
    }

    public User findUserByName(String userName) {
//        if (userRepo.findByName(userName).get() == null) {
//            throw new NoUserFoundException();
//        }
        return userRepo.findByName(userName).get();
    }
}
