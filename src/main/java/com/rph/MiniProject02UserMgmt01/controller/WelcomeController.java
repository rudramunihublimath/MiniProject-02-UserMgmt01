package com.rph.MiniProject02UserMgmt01.controller;

import com.rph.MiniProject02UserMgmt01.constants.AppConstants;
import com.rph.MiniProject02UserMgmt01.properties.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WelcomeController {

    @Autowired
    private AppProperties appProperties;

    @GetMapping("/welcome")
    public String getMsg(){
        Map<String, String> messages = appProperties.getMessages();
        String msg = messages.get(AppConstants.WELCOME_MSG);
        //String msg = "Welcome to our World..";
        return msg;
    }

}
