package com.example.myi18n.service;

import com.example.myi18n.entity.I18nAllocate;

import java.util.List;
import java.util.Map;

public interface I18nAllocateService {


    Map<String,Object> buildLangToWeb();

    List<I18nAllocate> buildLangToJava();

    I18nAllocate getI18nValue(String key);

    List<I18nAllocate> getMobelBag(String model);
}