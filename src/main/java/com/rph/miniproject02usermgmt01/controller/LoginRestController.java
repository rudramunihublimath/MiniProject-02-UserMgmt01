package com.rph.miniproject02usermgmt01.controller;

import com.rph.miniproject02usermgmt01.bindings.LoginForm;
import com.rph.miniproject02usermgmt01.exception.UserAppException;
import com.rph.miniproject02usermgmt01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) throws UserAppException {
        return userService.loginCheck(loginForm);
    }
}
