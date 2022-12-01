package com.shiji.distributedlock.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shiji.distributedlock.model.ShopStockModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShopStockModelMapper extends BaseMapper<ShopStockModel> {

    @Update("update shopStock set totalStock = totalStock - #{totalStock} where goodsCode = #{goodsCode} and totalStock >= #{totalStock}")
    public Integer updateStock(@Param("totalStock")Integer totalStock,@Param("goodsCode")String goodsCode);

    @Select("select * from shopStock where goodsCode = #{goodsCode} for update")
    public List<ShopStockModel> queryShopStock(@Param("goodsCode")String goodsCode);
}