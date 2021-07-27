package com.rph.miniproject02usermgmt01.bindings;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRegForm {
    private String fName;
    private String lName;
    private String email;
    private Long phno;
    private LocalDate dob;
    private String gender;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;
}
