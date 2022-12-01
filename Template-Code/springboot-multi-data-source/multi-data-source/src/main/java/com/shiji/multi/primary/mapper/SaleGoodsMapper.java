package com.shiji.multi.primary.mapper;

import com.shiji.multi.primary.entity.SaleGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SaleGoodsMapper {

    @Select("select * from salegoods where goodsName = #{goodsName}")
    public List<SaleGoods> findByName(@Param("goodsName") String goodsName);

}
