package com.rph.MiniProject02UserMgmt01.repo;


import com.rph.MiniProject02UserMgmt01.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccountEntity, Serializable> {

    public UserAccountEntity findByEmailAndPazzword(String email,String pazzword);

}
