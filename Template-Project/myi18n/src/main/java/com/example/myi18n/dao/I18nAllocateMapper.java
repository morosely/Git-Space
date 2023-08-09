package com.example.myi18n.dao;


import java.util.List;
import java.util.Map;

import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.entity.example.I18nAllocateExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


public interface I18nAllocateMapper {
    int countByExample(I18nAllocateExample example);

    int deleteByExample(I18nAllocateExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(I18nAllocate record);

    int insertSelective(I18nAllocate record);

    List<I18nAllocate> selectByExample(I18nAllocateExample example);

    I18nAllocate selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") I18nAllocate record, @Param("example") I18nAllocateExample example);

    int updateByExample(@Param("record") I18nAllocate record, @Param("example") I18nAllocateExample example);

    int updateByPrimaryKeySelective(I18nAllocate record);

    int updateByPrimaryKey(I18nAllocate record);


    List<I18nAllocate> buildLangToJava();
}