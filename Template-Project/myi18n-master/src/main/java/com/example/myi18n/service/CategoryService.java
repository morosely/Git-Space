package com.example.myi18n.service;

import com.example.myi18n.entity.Category;

import java.util.List;

public interface CategoryService {
     List<Category> selectList();

      void exceptionMsg() ;
}
