package com.rph.MiniProject02UserMgmt01.controller;

import com.rph.MiniProject02UserMgmt01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPwdRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/forgotPwd/{emailId}")
    public String forgotPwd(@PathVariable String emailId){
        boolean status = userService.forgotPwd(emailId);
        if(status){
            return "We have sent password to your email";
        }
        else {
            return "Please enter valid email";
        }

    }

}
