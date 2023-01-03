package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Data
@Repository("goods_sku")
public class Goods_sku {
    int id;//商品规格
    int goodsId;//商品id
    String name;//规格名称
    String goodsAttr;//商品属性json对象
    float price;//价格
    float originalPrice;//原价
    float costPrice;//进价
    int stock;//库存
    int bannersId;//轮播图集合id
    String bannerUrls;//轮播图照片
    int sortNo;//规格排序号
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}
