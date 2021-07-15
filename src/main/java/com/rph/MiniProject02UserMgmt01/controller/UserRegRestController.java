package com.rph.MiniProject02UserMgmt01.controller;

import com.rph.MiniProject02UserMgmt01.bindings.UserRegForm;
import com.rph.MiniProject02UserMgmt01.constants.AppConstants;
import com.rph.MiniProject02UserMgmt01.properties.AppProperties;
import com.rph.MiniProject02UserMgmt01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserRegRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppProperties appProperties;

    @GetMapping("/countries")
    public Map<Integer, String> getCountires() {
        return userService.getCountries();
    }

    @GetMapping("/states/{countryId}")
    public Map<Integer, String> getStates(@PathVariable Integer countryId) {
        return userService.getStates(countryId);
    }

    @GetMapping("/cities/{stateId}")
    public Map<Integer, String> getCities(@PathVariable Integer stateId) {
        return userService.getCities(stateId);
    }

    @GetMapping("/emailCheck/{email}")
    public String uniqueEmailCheck(@PathVariable String email) {
        String status = userService.emailCheck(email);
        return status;
    }

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody UserRegForm userRegForm) {
        boolean saveUser = userService.saveUser(userRegForm);
        Map<String, String> messages = appProperties.getMessages();
        if (saveUser) {
            return messages.get(AppConstants.USER_REG_SUCCESS);
        } else {
            return messages.get(AppConstants.USER_REG_FAIL);
        }
    }


}
