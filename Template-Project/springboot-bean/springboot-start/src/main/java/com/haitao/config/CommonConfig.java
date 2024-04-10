package com.haitao.config;

import com.yihaitao.model.GoodsModel;
import com.yihaitao.model.ShopModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class CommonConfig {

//    @Bean
//    public GoodsModel goodsModel(){
//        GoodsModel goodsModel = new GoodsModel(1L,"10086","中国移动",new BigDecimal("1999.00"));
//        return goodsModel;
//    }

    //@ConditionalOnPropert配置文件中存在对应的属性，才声明该bean
    @ConditionalOnProperty(prefix="goods",name={"goodscode","goodsname"})
    @Bean
    public GoodsModel goodsModel(@Value("${goods.goodscode}") String goodsCode,@Value("${goods.goodsname}") String goodsName){
        GoodsModel goodsModel = new GoodsModel();
        goodsModel.setGoodsCode(goodsCode);
        goodsModel.setGoodsName(goodsName);
        return goodsModel;
    }

    //当不存在当前类型的bean(GoodsModel)时，才声明该bean(ShopModel)
    //@ConditionalOnMissingBean(GoodsModel.class)
    @Bean
    //当前环境存在指定的这个类DispatcherServlet时，才声明该bean(ShopModel):当引入web起步依耐，则环境中有DispatcherServlet类，注入ShopModel。否则不注入ShopModel类
    @ConditionalOnClass(name="org.springframework.web.servlet.DispatcherServlet")
    public ShopModel shop(){
        return new ShopModel(1l,"001","螃蟹岬店");
    }
}
