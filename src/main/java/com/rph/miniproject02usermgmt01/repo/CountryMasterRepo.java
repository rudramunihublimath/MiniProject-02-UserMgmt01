package com.rph.miniproject02usermgmt01.repo;


import com.rph.miniproject02usermgmt01.entity.CountryMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Serializable> {
}
