package com.victoriaKlein.UserSignUpSignIn.controller;

import com.victoriaKlein.UserSignUpSignIn.securityAuth.SecurityService;
import com.victoriaKlein.UserSignUpSignIn.service.UserService;
import com.victoriaKlein.UserSignUpSignIn.model.User;
import com.victoriaKlein.UserSignUpSignIn.dto.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final SecurityService service;

    public UserController(UserService userService, SecurityService service) {
        this.userService = userService;
        this.service = service;
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Optional<User>> getUserNameById(@PathVariable("userId") Long id) {
        Optional<User> user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("user/{userName}")
    public ResponseEntity<String> getUserByName(@PathVariable("userName") String userName, RegistrationRequest request) {
        String s = userService.findUserByName(userName);
        request.setName(s);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping(value="/signUp",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<User> registerNewUser(@RequestBody @Valid RegistrationRequest request, BindingResult bindingResult) {
        User persistedUser = userService.add(new User(request.getName(), request.getPassword(), true, true, true));
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        service.autoLogIn(request.getName(), request.getPassword());
        return new ResponseEntity<>(persistedUser, HttpStatus.OK);
    }
}
