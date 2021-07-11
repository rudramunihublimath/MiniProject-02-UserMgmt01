package com.rph.MiniProject02UserMgmt01.bindings;

import lombok.Data;

@Data
public class UnlockAccForm {
    private String email;
    private String tempPwd;
    private String newPwd;
    //private String newPwd2;
}
