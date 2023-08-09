package com.example.myi18n.service.impl;

import com.example.myi18n.common.base.RunCustomException;
import com.example.myi18n.common.enums.ExceptionEnums;
import com.example.myi18n.dao.CategoryMapper;
import com.example.myi18n.entity.Category;
import com.example.myi18n.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> selectList(){
        return categoryMapper.selectByExample(null);
    }


    @Transactional
    public void exceptionMsg()  {
        try {
            int i = 2/0;
        }catch (Exception e){
            throw new RunCustomException(ExceptionEnums.SERVER_EXCEPTION.getSign());
        }
        String hi = "hi";
        System.out.println(hi);
    }

}
