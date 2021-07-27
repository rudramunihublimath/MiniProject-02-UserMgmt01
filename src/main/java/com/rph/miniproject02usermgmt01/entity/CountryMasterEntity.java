package com.rph.miniproject02usermgmt01.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "COUNTRY_MASTER")
public class CountryMasterEntity {
    @Id
    private Integer countryId;
    private Integer countryCode;
    private String countryName;


}
