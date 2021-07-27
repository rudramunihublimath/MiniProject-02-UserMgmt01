package com.rph.miniproject02usermgmt01.repo;


import com.rph.miniproject02usermgmt01.entity.CityMasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CityMasterRepo extends JpaRepository<CityMasterEntity, Serializable> {
    public List<CityMasterEntity> findByStateId(Integer stateId);
}
