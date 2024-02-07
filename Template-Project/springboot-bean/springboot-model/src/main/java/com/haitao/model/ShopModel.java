package com.haitao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopModel {
    private Long shopId;
    private String shopCode;
    private String shopName;
}
