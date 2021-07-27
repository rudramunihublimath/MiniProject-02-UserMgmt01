package com.rph.miniproject02usermgmt01.bindings;

import lombok.Data;

@Data
public class UnlockAccForm {
    private String email;
    private String tempPwd;
    private String newPwd;
}
