package com.jd.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jd.common.pojo.JDReturnResult;
import com.jd.common.redis.JedisClient;
import com.jd.mapper.TbOrderItemMapper;
import com.jd.mapper.TbOrderMapper;
import com.jd.mapper.TbOrderShippingMapper;
import com.jd.order.pojo.OrderInfo;
import com.jd.order.service.OrderService;
import com.jd.pojo.TbOrderItem;
import com.jd.pojo.TbOrderShipping;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;
	@Value("${ORDER_ID_BEGIN}")
	private String ORDER_ID_BEGIN;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;

	@Override
	public JDReturnResult createOrder(OrderInfo orderInfo) {
		// 1、接收表单的数据
		// 2、生成订单id
		if (!jedisClient.exists(ORDER_GEN_KEY)) {
			// 设置初始值
			jedisClient.set(ORDER_GEN_KEY, ORDER_ID_BEGIN);
		}
		String orderId = jedisClient.incr(ORDER_GEN_KEY).toString();
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		// 1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date date = new Date();
		orderInfo.setCreateTime(date);
		orderInfo.setUpdateTime(date);
		// 3、向订单表插入数据。
		orderMapper.insert(orderInfo);
		// 4、向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			// 生成明细id
			Long orderItemId = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY);
			tbOrderItem.setId(orderItemId.toString());
			tbOrderItem.setOrderId(orderId);
			// 插入数据
			orderItemMapper.insert(tbOrderItem);
		}
		// 5、向订单物流表插入数据。
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		// 6、返回TaotaoResult。
		return JDReturnResult.success(orderId);
	}

}
