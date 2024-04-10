package com.haitao.start;

import com.haitao.anno.EnableCommonConfig;
import com.yihaitao.model.GoodsModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@ComponentScan(basePackages = "com.haitao.config")
//@Import(CommonConfig.class)
//@Import(CommonImportSelector.class)
@EnableCommonConfig
public class SpringbootStartApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringbootStartApplication.class, args);

		System.out.println(" ==========>>> GoodsModel : " + context.getBean(GoodsModel.class));
		System.out.println(" ==========>>> ShopModel : " + context.getBean("shop"));
	}

	//@Bean：注入GoodsModel对象容器
//	@Bean
//	public GoodsModel goodsModel(){
//		return new GoodsModel();
//	}
}
