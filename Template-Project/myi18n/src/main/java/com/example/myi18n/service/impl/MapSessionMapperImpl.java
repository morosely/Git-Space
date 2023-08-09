package com.example.myi18n.service.impl;

import com.example.myi18n.config.MapResultHandler;
import com.example.myi18n.dao.NationnalityCodeMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class MapSessionMapperImpl extends SqlSessionDaoSupport  {
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public Map<String, Object> selectCountryMap() {
        MapResultHandler handler = new MapResultHandler();
        this.getSqlSession().select(NationnalityCodeMapper.class.getName() + ".buildLangToWeb", handler);
        Map<String, Object> map = handler.getMappedResults();
        return map;
    }
}
