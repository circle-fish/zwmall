package com.cnlbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository("goods")
public class Goods {

    private int id;//商品id
    private String name;//商品名称
    private String thumbUrl;//商品默认图片地址
    private float price;//默认价格
    private int categoryId;//商品类别id
    private int brandId;//商品品牌id
    private String title;//商品标题
    private String introduction;//商品介绍
    private int warningStock;//预警库存
    private int maxBuy;//单次最大购买量
    private String unit;//商品单位
    private int sketchType;//商品概述类型 0.图片 1.网页
    private String sketch;//商品概述
    private String specs;//商品参数
    private int skuType;//规格类型 0.单规格 1.多规格
    private int status;//状态 0.上架 1.下架
    private int buynum;//畅销情况
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

}