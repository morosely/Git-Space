package com.shiji.multi.secondary.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Goods {

    public Goods(String goodsName, String goodsCode) {
        this.goodsName = goodsName;
        this.goodsCode = goodsCode;
    }

    private String goodsName;
    private String goodsCode;

}