package com.shiji.multi;

import com.shiji.multi.primary.mapper.SaleGoodsMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ShiJiMultiDataSourceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ShiJiMultiDataSourceApplication.class, args);

		System.out.println("SaleGoodsMapper ==========>>> " + context.getBean(SaleGoodsMapper.class));
	}

}
