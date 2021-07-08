package com.rph.MiniProject02UserMgmt01.service;



import com.rph.MiniProject02UserMgmt01.bindings.LoginForm;
import com.rph.MiniProject02UserMgmt01.bindings.UnlockAccForm;
import com.rph.MiniProject02UserMgmt01.bindings.UserRegForm;

import java.util.Map;

public interface UserService {

    public String loginCheck(LoginForm loginForm);

    public Map<Integer, String> getCountries();
    public Map<Integer, String> getStates(Integer countryId);
    public Map<Integer, String> getCities(Integer stateId);

    public String emailCheck (String email);
    public boolean saveUser(UserRegForm userForm);
    public boolean unlockAccount(UnlockAccForm unloackAccForm);
    public boolean forgotPwd(String emailId);
}
