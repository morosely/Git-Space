package com.example.myi18n.controller;

import com.example.myi18n.common.base.BaseController;
import com.example.myi18n.common.base.ConverMapper;
import com.example.myi18n.common.component.I18nTemplateContainer;
import com.example.myi18n.common.base.ResultVO;
import com.example.myi18n.common.enums.ExceptionEnums;
import com.example.myi18n.entity.Category;
import com.example.myi18n.entity.I18nAllocate;
import com.example.myi18n.entity.Products;
import com.example.myi18n.entity.vo.I18nAllocateVO;
import com.example.myi18n.entity.vo.ProductsVO;
import com.example.myi18n.service.CategoryService;
import com.example.myi18n.service.I18nAllocateService;
import com.example.myi18n.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("Test")
public class TestController extends BaseController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private I18nTemplateContainer i18nTemplateContainer;
    @Autowired
    private I18nAllocateService i18nAllocateService;

    @RequestMapping("test")
    public ResultVO insertCategory(){
        List<Products> list = new ArrayList<>();
        Products products1 =new Products(1,"${category.#9999}",1.2,"1",1);
        Products products2 =new Products(1,"${category.#8888}",1.2,"1",1);
        list.add(products1);
        list.add(products2);
        return new ResultVO(list);
    }

    @RequestMapping("getCategoryList")
    public ResultVO getCategoryList(){
        List<Category> categories = categoryService.selectList();
        return new ResultVO(categories);
    }

    @RequestMapping("getProductsList")
    public ResultVO getProductsList(){
        List<Products> products = productsService.selectList();
        List<ProductsVO> productsVOS = ConverMapper.INSTANCE.cproductsToListProductsVO(products);
        return new ResultVO(productsVOS);
    }


    @RequestMapping("getProductsKey")
    public ResultVO getProductsKey(Integer pid){
        Products products = productsService.getProductKeyId(pid);
        ProductsVO productsVO = ConverMapper.INSTANCE.cProductsToProductsVO(products);
        return new ResultVO(productsVO);
    }


    @RequestMapping("exception")
    public ResultVO exception(){
        //异常处理国际化问题
        return new ResultVO(500, ExceptionEnums.SERVER_EXCEPTION,null);
    }


    @RequestMapping("response")
    public ResultVO response(){
        return ResultVO.success(ExceptionEnums.SERVER_EXCEPTION.getSign());
    }

    @RequestMapping("exceptionMsg")
    public ResultVO exceptionMsg()  {
        //自动捕捉异常并国际化处理
        categoryService.exceptionMsg();
        return ResultVO.failure("错了错了");
    }

    @RequestMapping("getI18nBag")
    public ResultVO getI18nBag(String key){
        //前端访问国际化标识，返回数据
        String value = i18nTemplateContainer.getValue(key);
        return ResultVO.success(value);
    }

    @RequestMapping("getMobelBag")
    public ResultVO getMobelBag(String model){
        //前端获取模块中所有国际化的数据
        List<I18nAllocate> mobelBag = i18nAllocateService.getMobelBag(model);
        List<I18nAllocateVO> i18nAllocateVOS = ConverMapper.INSTANCE.cI18nAllocateToI18nAllocateVo(mobelBag);
        return ResultVO.success(i18nAllocateVOS);
    }

}
