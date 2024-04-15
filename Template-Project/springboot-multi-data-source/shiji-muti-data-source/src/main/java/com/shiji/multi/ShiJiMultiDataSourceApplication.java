package com.shiji.multi;

import com.shiji.multi.primary.mapper.GoodsMapper;
import com.shiji.multi.primary.mapper.SaleGoodsMapper;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

@SpringBootApplication
public class ShiJiMultiDataSourceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ShiJiMultiDataSourceApplication.class, args);
		System.out.println("SaleGoodsMapper ==========>>> " + context.getBean(SaleGoodsMapper.class));
		System.out.println("GoodsMapper 【primary】==========>>> " + context.getBean(GoodsMapper.class));
		System.out.println("GoodsMapper 【secondary】==========>>> " + context.getBean(com.shiji.multi.secondary.mapper.GoodsMapper.class));

	}
}
