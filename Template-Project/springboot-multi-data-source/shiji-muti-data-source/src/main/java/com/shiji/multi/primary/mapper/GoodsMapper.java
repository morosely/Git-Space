package com.shiji.multi.primary.mapper;

import com.shiji.multi.primary.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

//@Qualifier("primaryGoodsMapper")
public interface GoodsMapper {

    @Select("select * from goods where goodsName = #{goodsName}")
    public List<Goods> findByName(@Param("goodsName") String goodsName);

}
