package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@Repository("cart")
public class Cart {
    int id; //购物车编号;
    int userId;//用户id
    int goodsId;//商品id
    int skuId;//规格id
    String name;//商品名
    String thumbUrl; //图片url
    float price; // 商品单价
    int count; // 购物车中该商品的数量
    int checked;//选中状态 1.选中 0.未选中
    Date createTime;//创建时间
    Date updateTime;//更新时间
}