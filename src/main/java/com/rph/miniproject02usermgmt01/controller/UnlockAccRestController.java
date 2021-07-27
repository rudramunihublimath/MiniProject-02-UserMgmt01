package com.rph.miniproject02usermgmt01.controller;

import com.rph.miniproject02usermgmt01.bindings.UnlockAccForm;
import com.rph.miniproject02usermgmt01.exception.UserAppException;
import com.rph.miniproject02usermgmt01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnlockAccRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/unlock")
    public String unlockUserAcc(@RequestBody UnlockAccForm unlockAccForm) throws UserAppException {
        boolean unlockAccount = userService.unlockAccount(unlockAccForm);
        if (unlockAccount) {
            return "Account Unlocked Successfully";
        } else {
            return "Incorrect Temporary password";
        }
    }
}
