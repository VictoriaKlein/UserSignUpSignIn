package com.victoriaKlein.UserSignUpSignIn.repository;

import com.victoriaKlein.UserSignUpSignIn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long>, UserRepoCustom {
    @Query("UPDATE User u SET u.failedAttempt = ?1 WHERE u.password = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String pass);

    @Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
    @Modifying
    public void updateEnabledStatus(@Param("id") Integer id, @Param("enabled") boolean enabled);
}
