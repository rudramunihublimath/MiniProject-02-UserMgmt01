package com.rph.miniproject02usermgmt01.service;


import com.rph.miniproject02usermgmt01.bindings.LoginForm;
import com.rph.miniproject02usermgmt01.bindings.UnlockAccForm;
import com.rph.miniproject02usermgmt01.bindings.UserRegForm;
import com.rph.miniproject02usermgmt01.constants.AppConstants;
import com.rph.miniproject02usermgmt01.entity.CityMasterEntity;
import com.rph.miniproject02usermgmt01.entity.CountryMasterEntity;
import com.rph.miniproject02usermgmt01.entity.StateMasterEntity;
import com.rph.miniproject02usermgmt01.entity.UserAccountEntity;
import com.rph.miniproject02usermgmt01.exception.UserAppException;
import com.rph.miniproject02usermgmt01.properties.AppProperties;
import com.rph.miniproject02usermgmt01.repo.CityMasterRepo;
import com.rph.miniproject02usermgmt01.repo.CountryMasterRepo;
import com.rph.miniproject02usermgmt01.repo.StateMasterRepo;
import com.rph.miniproject02usermgmt01.repo.UserAccountRepo;
import com.rph.miniproject02usermgmt01.util.EmailUtils;
import com.rph.miniproject02usermgmt01.util.PwdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserAccountRepo userRepo;
    @Autowired
    private CountryMasterRepo countryRepo;
    @Autowired
    private StateMasterRepo stateRepo;
    @Autowired
    private CityMasterRepo cityRepo;
    @Autowired
    private AppProperties appProps;
    @Autowired
    private EmailUtils emailUtils;

    private static String generateRandomPazzwrd(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(AppConstants.CANDIDATE_CHARS.charAt(
                    random.nextInt(AppConstants.CANDIDATE_CHARS.length())));
        }
        return sb.toString();
    }

    @Override
    public String loginCheck(LoginForm loginForm) throws UserAppException {
        String msg;
        String encryptedPwd = null;
        encryptedPwd = PwdUtils.encryptMsg(loginForm.getPwd());

        UserAccountEntity user = userRepo.findByEmailAndPazzword(loginForm.getEmail(), encryptedPwd);
        if (user != null) {
            String accStatus = user.getAccStatus();
            if (AppConstants.LOCKED.equals(accStatus)) {
                msg = appProps.getMessages().get(AppConstants.ACC_LOCKED);
            } else {
                msg = AppConstants.SUCCESS;
            }
        } else {
            msg = appProps.getMessages().get(AppConstants.INVALID_CREDENTIALS);
        }
        return msg;
    }

    @Override
    public Map<Integer, String> getCountries() {
        List<CountryMasterEntity> countries = countryRepo.findAll();

        Map<Integer, String> countryMap = new HashMap<>();
        countries.forEach(country -> countryMap.put(country.getCountryId(), country.getCountryName()));
        return countryMap;

    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);

        Map<Integer, String> stateMap = new HashMap<>();
        states.forEach(state -> stateMap.put(state.getStateId(), state.getStateName()));
        return stateMap;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);

        Map<Integer, String> cityMap = new HashMap<>();
        cities.forEach(city -> cityMap.put(city.getCityId(), city.getCityName()));
        return cityMap;
    }

    @Override
    public String emailCheck(String emailId) {
        Optional<UserAccountEntity> findOne = getUserByEmail(emailId);
        if (findOne.isPresent()) {
            return AppConstants.DUPLICATE;
        } else {
            return AppConstants.UNIQUE;
        }
    }

    @Override
    public boolean saveUser(UserRegForm userForm) throws UserAppException {
        UserAccountEntity entity = new UserAccountEntity();
        BeanUtils.copyProperties(userForm, entity);
        entity.setAccStatus(AppConstants.LOCKED);
        String randomPwd = generateRandomPazzwrd(6);
        String encryptedPwd;

        encryptedPwd = PwdUtils.encryptMsg(randomPwd);
        entity.setPazzword(encryptedPwd);

        entity = userRepo.save(entity);

        String emailBody = readUnlockAccEmailBody(entity);
        String subject = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);
        try {
            emailUtils.sendEmail(userForm.getEmail(), subject, emailBody);
        } catch (Exception e) {
            logger.error(AppConstants.EXCEPTION_OCCURRED + e.getMessage(), e);
            throw new UserAppException(e.getMessage());
        }

        return entity.getUserId() != null ? true : false;
    }

    private String readUnlockAccEmailBody(UserAccountEntity entity) throws UserAppException {
        StringBuilder sb = new StringBuilder(AppConstants.EMPTY_STR);
        String mailBody = AppConstants.EMPTY_STR;
        String fileName = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();

            String decryptedPwd = PwdUtils.decryptMsg(entity.getPazzword());

            mailBody = sb.toString();
            mailBody = mailBody.replace(AppConstants.FNAME, entity.getFName());
            mailBody = mailBody.replace(AppConstants.LNAME, entity.getLName());
            mailBody = mailBody.replace(AppConstants.TEMP_PAZZWD, decryptedPwd);
            mailBody = mailBody.replace(AppConstants.EMAIL, entity.getEmail());
        } catch (Exception e) {
            logger.error(AppConstants.EXCEPTION_OCCURRED + e.getMessage(), e);
            throw new UserAppException(e.getMessage());
        }
        return mailBody;
    }

    @Override
    public boolean unlockAccount(UnlockAccForm unlockAccForm) throws UserAppException {
        String email = unlockAccForm.getEmail();
        String tempPwd = unlockAccForm.getTempPwd();
        String encryptedPwd = null;

        encryptedPwd = PwdUtils.encryptMsg(tempPwd);


        UserAccountEntity user = userRepo.findByEmailAndPazzword(email, encryptedPwd);

        if (user != null) {
            String newPwd = unlockAccForm.getNewPwd();
            String encryptedNewPwd = null;

            encryptedNewPwd = PwdUtils.encryptMsg(newPwd);
            user.setPazzword(encryptedNewPwd);

            user.setAccStatus(AppConstants.UNLOCKED);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean forgotPwd(String emailId) throws UserAppException {
        Optional<UserAccountEntity> findOne = getUserByEmail(emailId);
        if (findOne.isPresent()) {
            UserAccountEntity userEntity = findOne.get();
            String emailBody = readForgotPwdEmailBody(userEntity);
            String subject = appProps.getMessages().get(AppConstants.RECOVER_PAZZWD_EMAIL_SUB);
            try {
                emailUtils.sendEmail(emailId, subject, emailBody);
            } catch (Exception e) {
                logger.error(AppConstants.EXCEPTION_OCCURRED + e.getMessage(), e);
                throw new UserAppException(e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    private String readForgotPwdEmailBody(UserAccountEntity userEntity) throws UserAppException {
        StringBuilder sb = new StringBuilder(AppConstants.EMPTY_STR);
        String mailBody = AppConstants.EMPTY_STR;
        String fileName = appProps.getMessages().get(AppConstants.RECOVER_PAZZWD_EMAIL_BODY_FILE);
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            String decryptedPwd = PwdUtils.decryptMsg(userEntity.getPazzword());
            mailBody = sb.toString();
            mailBody = mailBody.replace(AppConstants.FNAME, userEntity.getFName());
            mailBody = mailBody.replace(AppConstants.LNAME, userEntity.getLName());
            mailBody = mailBody.replace(AppConstants.PAZZWD, decryptedPwd);
        } catch (Exception e) {
            logger.error(AppConstants.EXCEPTION_OCCURRED + e.getMessage(), e);
            throw new UserAppException(e.getMessage());
        }
        return mailBody;
    }

    private Optional<UserAccountEntity> getUserByEmail(String emailId) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setEmail(emailId);

        Example<UserAccountEntity> example = Example.of(entity);
        return userRepo.findOne(example);
    }
}
