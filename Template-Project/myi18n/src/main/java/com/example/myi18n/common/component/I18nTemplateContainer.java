package com.example.myi18n.common.component;

import com.example.myi18n.common.contants.I18nContants;
import com.example.myi18n.common.contants.RedisKeyContants;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.service.I18nAllocateService;
import com.example.myi18n.service.redis.RedisService;
import com.example.myi18n.utils.I18nUtils;
import com.example.myi18n.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class I18nTemplateContainer {
    @Autowired
    private RedisService redisService;
    @Autowired
    private I18nAllocateService i18nAllocateService;
    private Map<String , Object> i18nMap = new ConcurrentHashMap<>();
    private boolean flot = true;

    public String getValue(String key){
        if (null==key){
            return null;
        }
        List<String> params = I18nUtils.findParams(key);
        if (null!=params && params.size()>=1){
            key= params.get(0);
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String language = request.getHeader(I18nContants.LANGUAGE) != null ?  request.getHeader(I18nContants.LANGUAGE) : I18nContants.DEFAULT_LANGUAGE  ;
        if (flot){
            String json = redisService.get(RedisKeyContants.LANGUAGE_ZONE);
            if (null!=json){
                Map<String,Object> map = (Map<String, Object>) JsonUtils.toMap(json);
                i18nMap.putAll(map);
            }
            flot =false;
        }
        Object obj = i18nMap.get(key);
        if (null != obj){
            String value = I18nUtils.getLanguageValue(language, obj.toString());
            return value;
        }
        I18nAllocate i18nValue = i18nAllocateService.getI18nValue(key);
        if (null != i18nValue){
            i18nMap.putIfAbsent(key, i18nValue.getLangs());
            String value = I18nUtils.getLanguageValue(language, i18nValue.getLangs());
            return value;
        }
        return null;
    }


}
