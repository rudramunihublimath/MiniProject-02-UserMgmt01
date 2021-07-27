package com.rph.miniproject02usermgmt01.service;


import com.rph.miniproject02usermgmt01.bindings.LoginForm;
import com.rph.miniproject02usermgmt01.bindings.UnlockAccForm;
import com.rph.miniproject02usermgmt01.bindings.UserRegForm;
import com.rph.miniproject02usermgmt01.exception.UserAppException;

import java.util.Map;

public interface UserService {

    public String loginCheck(LoginForm loginForm) throws UserAppException;

    public Map<Integer, String> getCountries();

    public Map<Integer, String> getStates(Integer countryId);

    public Map<Integer, String> getCities(Integer stateId);

    public String emailCheck(String email);

    public boolean saveUser(UserRegForm userForm) throws UserAppException;

    public boolean unlockAccount(UnlockAccForm unloackAccForm) throws UserAppException;

    public boolean forgotPwd(String emailId) throws UserAppException;
}
