package com.shiji.distributedlock.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.intellij.lang.annotations.Identifier;

import java.util.Date;

/**
 * 商品门店库存表
 * shopStock
 * @author Administrator
 * @date 2022-11-24 19:57:46
 */
@Data
@TableName("shopStock")
public class ShopStockModel {
    /**
     * 门店商品库存ID
     */
    private Long id;

    /**
     * 门店编码
     */
    private String shopCode;

    /**
     * 门店名称
     */
    private String shopName;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 库存总量
     */
    private Long totalStock;

    /**
     * 不可卖库存
     */
    private Integer unsaleStock;

    /**
     * 当天出库销售库存
     */
    private Long usedStock;

    /**
     * 锁定库存
     */
    private Long lockStock;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 最后更新时间
     */
    private Date updateDate;

    /**
     * 版本号
     */
    private Integer version;


}