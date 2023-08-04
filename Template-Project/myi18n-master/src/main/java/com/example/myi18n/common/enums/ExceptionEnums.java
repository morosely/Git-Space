package com.example.myi18n.common.enums;

import com.example.myi18n.common.contants.StrinfContants;

public  enum ExceptionEnums {

    SERVER_EXCEPTION("exception","@1");

    ExceptionEnums(String module,String label) {
        this.module = module;
        this.label = label;
    }
    private String module ;
    private String label;
    public String getSign() {
        return StrinfContants.I18N_PREFIX + module + StrinfContants.SPOT + label + StrinfContants.I18N_POSTFIX ;
    }
}
