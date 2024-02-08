package com.yiihaitao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsModel {
    private Long id;
    private String goodsCode;
    private String goodsName;
    private BigDecimal salePrice;
}
