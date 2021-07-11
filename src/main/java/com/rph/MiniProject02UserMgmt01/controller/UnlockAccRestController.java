package com.rph.MiniProject02UserMgmt01.controller;

import com.rph.MiniProject02UserMgmt01.bindings.UnlockAccForm;
import com.rph.MiniProject02UserMgmt01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnlockAccRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/unlock")
    public String unlockUserAcc(@RequestBody UnlockAccForm unlockAccForm) {
        boolean unlockAccount = userService.unlockAccount(unlockAccForm);
        if(unlockAccount){
            return "Account Unlocked Successfully";
        }
        else {
            return "Incorrect Temporary password";
        }
    }
}
