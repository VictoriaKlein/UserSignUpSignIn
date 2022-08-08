package com.victoriaKlein.UserSignUpSignIn.repository;

import com.victoriaKlein.UserSignUpSignIn.model.User;

import java.util.Optional;

public interface UserRepoCustom {
    Optional<User> findByName(String name);
}
