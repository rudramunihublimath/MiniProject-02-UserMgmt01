package com.rph.miniproject02usermgmt01.repo;


import com.rph.miniproject02usermgmt01.entity.StateMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface StateMasterRepo extends JpaRepository<StateMasterEntity, Serializable> {
    public List<StateMasterEntity> findByCountryId(Integer countryId);
}
