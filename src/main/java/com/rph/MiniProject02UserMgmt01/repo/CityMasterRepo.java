package com.rph.MiniProject02UserMgmt01.repo;


import com.rph.MiniProject02UserMgmt01.entity.CityMasterEntity;
import com.rph.MiniProject02UserMgmt01.entity.StateMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Serializable> {
    public List<CityMasterEntity> findByStateId(Integer stateId);
}
