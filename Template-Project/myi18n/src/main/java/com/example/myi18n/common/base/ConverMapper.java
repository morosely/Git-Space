package com.example.myi18n.common.base;

import com.example.myi18n.entity.Category;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.entity.Products;
import com.example.myi18n.entity.vo.CategoryVO;
import com.example.myi18n.entity.vo.I18nAllocateVO;
import com.example.myi18n.entity.vo.ProductsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * POJO映射装换
 * @author DuanLinpeng
 * @date 2021/01/09 22:12
 **/
@Mapper
public interface ConverMapper {

    ConverMapper INSTANCE = Mappers.getMapper(ConverMapper.class);

    List<I18nAllocateVO> cI18nAllocateToI18nAllocateVo(List<I18nAllocate> studentList);

    ProductsVO cProductsToProductsVO(Products products);

    List<ProductsVO> cproductsToListProductsVO(List<Products> list);

}
