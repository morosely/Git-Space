package com.shiji.multi;

import com.shiji.multi.primary.entity.SaleGoods;
import com.shiji.multi.primary.mapper.SaleGoodsMapper;
import com.shiji.multi.secondary.entity.Goods;
import com.shiji.multi.secondary.mapper.GoodsMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ShiJiMultiDataSourceApplicationTests {

	@Autowired
	private SaleGoodsMapper saleGoodsMapper;
	@Autowired
	private GoodsMapper goodsMapper;


	@Test
	public void test() throws Exception {
		List<SaleGoods> primaryList =  saleGoodsMapper.findByName("大蔥");
		System.out.println("primaryList = " + primaryList);

		List<Goods> secondaryList =  goodsMapper.findByName("大蔥");
		System.out.println("secondaryList = " + secondaryList);
	}

}
