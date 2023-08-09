package com.example.myi18n.dao;

import java.util.List;

import com.example.myi18n.entity.Category;
import com.example.myi18n.entity.Products;
import com.example.myi18n.entity.example.ProductsExample;
import com.example.myi18n.entity.vo.ProductsVO;
import org.apache.ibatis.annotations.Param;

public interface ProductsMapper {
    int countByExample(ProductsExample example);

    int deleteByExample(ProductsExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Products record);

    int insertSelective(Products record);

    List<Products> selectByExample(ProductsExample example);

    Products selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Products record, @Param("example") ProductsExample example);

    int updateByExample(@Param("record") Products record, @Param("example") ProductsExample example);

    int updateByPrimaryKeySelective(Products record);

    int updateByPrimaryKey(Products record);

    Products getKeyId(@Param("pid") Integer pid);

    List<Products> selectAllList();
}