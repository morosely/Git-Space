package com.shiji.multi.secondary.mapper;

import com.shiji.multi.secondary.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
//@Qualifier("secondaryGoodsMapper")
public interface GoodsMapper {

    @Select("select * from goods where goodsName = #{goodsName}")
    public List<Goods> findByName(@Param("goodsName") String goodsName);

}
