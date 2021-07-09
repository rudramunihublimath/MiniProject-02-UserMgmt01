package com.rph.MiniProject02UserMgmt01.service;



import com.rph.MiniProject02UserMgmt01.bindings.LoginForm;
import com.rph.MiniProject02UserMgmt01.bindings.UnlockAccForm;
import com.rph.MiniProject02UserMgmt01.bindings.UserRegForm;
import com.rph.MiniProject02UserMgmt01.constants.AppConstants;
import com.rph.MiniProject02UserMgmt01.entity.CityMasterEntity;
import com.rph.MiniProject02UserMgmt01.entity.CountryMasterEntity;
import com.rph.MiniProject02UserMgmt01.entity.StateMasterEntity;
import com.rph.MiniProject02UserMgmt01.entity.UserAccountEntity;
import com.rph.MiniProject02UserMgmt01.properties.AppProperties;
import com.rph.MiniProject02UserMgmt01.repo.CityMasterRepo;
import com.rph.MiniProject02UserMgmt01.repo.CountryMasterRepo;
import com.rph.MiniProject02UserMgmt01.repo.StateMasterRepo;
import com.rph.MiniProject02UserMgmt01.repo.UserAccountRepo;
import com.rph.MiniProject02UserMgmt01.util.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
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

    @Override
    public String loginCheck(LoginForm loginForm) {
        String msg;
        UserAccountEntity user = userRepo.findByEmailAndPazzword(loginForm.getEmail(),loginForm.getPwd());
        if(user!=null){
            String accStatus = user.getAccStatus();
            if(AppConstants.LOCKED.equals(accStatus)){
                msg = appProps.getMessages().get(AppConstants.ACC_LOCKED);
            }
            else {
                msg = AppConstants.SUCCESS;
            }
        }
        else {
            msg = appProps.getMessages().get(AppConstants.INVALID_CREDENTIALS);
        }
        return msg;
    }

    @Override
    public Map<Integer, String> getCountries() {
        List<CountryMasterEntity> countries = countryRepo.findAll();

        Map<Integer, String> countryMap = new HashMap<>();
        countries.forEach( country -> {
            countryMap.put(country.getCountryId(), country.getCountryName());
        });
        return countryMap;

    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);

        Map<Integer, String> stateMap = new HashMap<>();
        states.forEach( state -> {
            stateMap.put(state.getStateId(), state.getStateName());
        });
        return stateMap;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);

        Map<Integer, String> cityMap = new HashMap<>();
        cities.forEach( city -> {
            cityMap.put(city.getCityId(), city.getCityName());
        });
        return cityMap;
    }

    @Override
    public String emailCheck(String emailId) {
        Optional<UserAccountEntity> findOne = getUserByEmail(emailId);
        if(findOne.isPresent()){
            return AppConstants.DUPLICATE;
        }
        else {
            return AppConstants.UNIQUE;
        }
    }

    @Override
    public boolean saveUser(UserRegForm userForm) {
        UserAccountEntity entity = new UserAccountEntity();
        BeanUtils.copyProperties(userForm,entity);
        entity.setAccStatus(AppConstants.LOCKED);
        entity.setPazzword(generateRandomPazzwrd(6));
        // TODO: password encryption Functionality
        entity = userRepo.save(entity);
        // EMAIL Functionality
        String emailBody = readUnlockAccEmailBody(entity);
        String subject = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);
        boolean status = emailUtils.sendEmail(userForm.getEmail(), subject, emailBody);
        return entity.getUserId()!=null ? true : false ;
    }

    private static String generateRandomPazzwrd(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(AppConstants.CANDIDATE_CHARS.charAt(
                    random.nextInt(AppConstants.CANDIDATE_CHARS.length())));
        }
        return sb.toString();
    }

    private String readUnlockAccEmailBody(UserAccountEntity entity) {
        StringBuffer sb = new StringBuffer(AppConstants.EMPTY_STR);
        String mailBody = AppConstants.EMPTY_STR;
        try {
            String fileName = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_BODY_FILE);
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();

            mailBody = sb.toString();
            mailBody = mailBody.replaceAll(AppConstants.FNAME, entity.getFName());
            mailBody = mailBody.replaceAll(AppConstants.LNAME, entity.getLName());
            mailBody = mailBody.replaceAll(AppConstants.TEMP_PWD, entity.getPazzword());
            mailBody = mailBody.replaceAll(AppConstants.EMAIL, entity.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mailBody;
    }

    @Override
    public boolean unlockAccount(UnlockAccForm unlockAccForm) {
        String email = unlockAccForm.getEmail();
        String tempPwd = unlockAccForm.getTempPwd();
        UserAccountEntity user = userRepo.findByEmailAndPazzword(email, tempPwd);

        if(user!=null) {
            user.setPazzword(unlockAccForm.getNewPwd1());
            user.setAccStatus(AppConstants.UNLOCKED);
            userRepo.save(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean forgotPwd(String emailId) {
        Optional<UserAccountEntity> findOne = getUserByEmail(emailId);
        if(findOne.isPresent()){
            UserAccountEntity userEntity = findOne.get();
            String email = userEntity.getEmail();
            String pazzword = userEntity.getPazzword();
            // TODO: EMAIL to user
            return true;
        }
        else {
            return false;
        }
    }

    private Optional<UserAccountEntity> getUserByEmail(String emailId) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setEmail(emailId);

        Example<UserAccountEntity> example = Example.of(entity);
        return userRepo.findOne(example);
    }
}
