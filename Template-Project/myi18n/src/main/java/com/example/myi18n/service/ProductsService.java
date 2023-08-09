package com.example.myi18n.service;

import com.example.myi18n.entity.Products;
import com.example.myi18n.entity.vo.ProductsVO;

import java.util.List;

public interface ProductsService {
     List<Products> selectList();

     Products getProductKeyId(Integer pid);
}
