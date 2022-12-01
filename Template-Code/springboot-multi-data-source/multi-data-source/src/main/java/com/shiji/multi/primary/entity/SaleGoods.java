package com.shiji.multi.primary.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SaleGoods {

    public SaleGoods(String goodsName, String goodsCode) {
        this.goodsName = goodsName;
        this.goodsCode = goodsCode;
    }

    private String goodsName;
    private String goodsCode;

}