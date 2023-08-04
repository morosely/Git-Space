package com.example.myi18n.service.impl;

import com.example.myi18n.common.contants.StrinfContants;
import com.example.myi18n.dao.I18nAllocateMapper;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.entity.example.I18nAllocateExample;
import com.example.myi18n.service.I18nAllocateService;
import com.example.myi18n.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class I18nAllocateServiceImpl implements I18nAllocateService {
    @Autowired
    private I18nAllocateMapper i18nAllocateMapper;
    @Autowired
    private MapSessionMapperImpl mapSessionMapper;

    @Override
    public Map<String,Object> buildLangToWeb() {
        Map<String, Object> map = mapSessionMapper.selectCountryMap();
        return  map;
    }

    @Override
    public List<I18nAllocate> buildLangToJava() {
        return i18nAllocateMapper.buildLangToJava();
    }


    @Override
    public I18nAllocate getI18nValue(String key) {
        if (key==null){
            return null;
        }
        I18nAllocateExample example = new I18nAllocateExample();
        String[] split = key.split(StrinfContants.SPLIT);
        if (split.length > 1){
            example.createCriteria().andModuleEqualTo(split[0]).andLabelEqualTo(split[1]);
        }else{
            example.createCriteria().andLabelEqualTo(split[0]);
        }

        List<I18nAllocate> i18nAllocates = i18nAllocateMapper.selectByExample(example);
        if (null != i18nAllocates && i18nAllocates.size()>0){
            return i18nAllocates.get(0);
        }
        return null;
    }

    @Override
    public List<I18nAllocate> getMobelBag(String model) {
        if (null == model){
            return null;
        }
        I18nAllocateExample example = new I18nAllocateExample();
        example.createCriteria().andModuleEqualTo(model);
        List<I18nAllocate> i18nAllocates = i18nAllocateMapper.selectByExample(example);
        return i18nAllocates;
    }

}
