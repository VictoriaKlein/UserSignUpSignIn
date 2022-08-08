package com.victoriaKlein.UserSignUpSignIn.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
@Table(name = "registered_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name", "user_password"})})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", nullable = false, unique = true)
    @NotEmpty(message = "User name shouldn't be empty")
    private String name;
    @Column(name = "user_password", nullable = false, unique = true)
    @NotEmpty(message = "Password shouldn't be empty")
    private String password;
    private boolean enabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt;

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    private boolean isCredentialsNonExpired;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    @Column(name = "lock_time")
    private Date lockTime;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String name, String password, boolean enabled, boolean accountNonLocked, boolean isCredentialsNonExpired) {
        this.name = name;
        this.password = password;
        this.enabled = enabled;
        this.accountNonLocked = accountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

}
