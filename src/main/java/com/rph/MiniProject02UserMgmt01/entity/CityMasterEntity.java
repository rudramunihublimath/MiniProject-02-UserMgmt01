package com.rph.MiniProject02UserMgmt01.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "CITIES_MASTER")
public class CityMasterEntity {
    @Id
    private Integer cityId;
    private Integer stateId;
    private String cityName;


}
