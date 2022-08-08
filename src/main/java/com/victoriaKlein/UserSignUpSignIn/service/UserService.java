package com.victoriaKlein.UserSignUpSignIn.service;

import com.victoriaKlein.UserSignUpSignIn.exception.NoUserFoundException;
import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.repository.AnimalRepo;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepo;
import com.victoriaKlein.UserSignUpSignIn.repository.UserRepoCustom;
import com.victoriaKlein.UserSignUpSignIn.securityAuth.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = "user with name %S not found";
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final UserRepoCustom userRepoCustom;
    @Autowired
    private final AnimalRepo animalRepo;

    public UserService(UserRepo userRepo, UserRepoCustom userRepoCustom, AnimalRepo animalRepo) {
        this.userRepoCustom = userRepoCustom;
        this.userRepo = userRepo;
        this.animalRepo = animalRepo;
    }

    public Optional<User> getUser(Long id) {
        Optional<User> u = userRepo.findById(id);
        if (!u.isPresent())
            throw new NoSuchElementException("Not found user with id = " + id);
        return u;
    }
    public String findUserByName(String userName) {
        Optional<User> u = userRepoCustom.findByName(userName);
        if (u.isPresent()) {
            throw new IllegalStateException("user with this name already exist");
        }
        return userName;
    }
    public User add(User user) {
        userRepo.save(user);
        return user;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepoCustom.findByName(username).get();
        if (user == null) {
            throw new NoUserFoundException();
        }
        return new AuthUserDetails(user);
    }


}
