package com.rph.miniproject02usermgmt01.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "USER_ACCOUNTS")
public class UserAccountEntity {
    @Id
    @GeneratedValue
    @Column(name="USER_ID")
    private Integer userId;

    @Column(name="FIRST_NAME")
    private String fName;
    @Column(name="LAST_NAME")
    private String lName;

    @Column(name="USER_EMAIL",unique = true)
    private String email;
    @Column(name="USER_PWD")
    private String pazzword;
    @Column(name="USER_MOBILE")
    private Long phno;

    @Column(name="DOB")
    private LocalDate dob;
    @Column(name="Gender")
    private String gender;
    @Column(name="ACC_STATUS")
    private String accStatus;

    @Column(name="CITY_ID")
    private Integer cityId;
    @Column(name="STATE_ID")
    private Integer stateId;
    @Column(name="COUNTRY_ID")
    private Integer countryId;

    @CreationTimestamp
    @Column(name = "CREATED_DATE",updatable = false)
    private LocalDate createdDate;

    @UpdateTimestamp
    @Column(name = "UPDATED_DATE",insertable = false)
    private LocalDate updatedDate;

}
