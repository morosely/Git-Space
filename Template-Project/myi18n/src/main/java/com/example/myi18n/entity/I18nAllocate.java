package com.example.myi18n.entity;

import com.example.myi18n.common.contants.I18nContants;
import com.example.myi18n.common.contants.StrinfContants;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class I18nAllocate {
    private Integer pid;

    private String type;

    private String module;

    private String label;

    private String langs;

    private Integer toWeb;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getLangs() {
        return langs;
    }

    public void setLangs(String langs) {
        this.langs = langs;
    }

    public Integer getToWeb() {
        return toWeb;
    }

    public void setToWeb(Integer toWeb) {
        this.toWeb = toWeb;
    }


    public final boolean equalsCombination(String param){
        if (null == param){
            return false;
        }
        String strSplit = this.module + StrinfContants.SPOT + this.label;
        return strSplit.equals(param);
    }
}