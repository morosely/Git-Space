package com.jd.portal.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jd.pojo.TbItem;
import com.jd.pojo.TbItemDesc;
import com.jd.portal.pojo.Item;
import com.jd.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMesssageListener implements MessageListener {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;
	@Override
	public void onMessage(Message message) {
		try {
			//从消息中取商品id
			TextMessage textMessage = (TextMessage) message;
			String strId = textMessage.getText();
			Long itemId = Long.parseLong(strId);
			//等待事务提交
			Thread.sleep(1000);
			//根据商品id查询商品信息及商品描述
			TbItem tbItem = itemService.getItemById(itemId);
			Item item = new Item();
			BeanUtils.copyProperties(tbItem,item,getNullPropertyNames(tbItem));
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			//使用freemarker生成静态页面
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			//1.创建模板
			//2.加载模板对象
			Template template = configuration.getTemplate("item.ftl");
			//3.准备模板需要的数据
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			//4.指定输出的目录及文件名
			Writer out = new FileWriter(new File(HTML_OUT_PATH + strId + ".html"));
			//5.生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

}
