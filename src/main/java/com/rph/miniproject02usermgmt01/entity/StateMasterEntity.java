package com.rph.miniproject02usermgmt01.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "STATES_MASTER")
public class StateMasterEntity {
    @Id
    private Integer stateId;
    private Integer countryId;
    private String stateName;


}
