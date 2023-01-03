package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Data
@Repository("order_goods")

public class Order_goods {

    //Table structure for order_goods
    private int id;
    private String orderNo;
    private int goodsId;//商品id
    private int skuId;//规格id
    private String name;//商品名
    private String thumbUrl; //图片url
    private float price; // 商品单价
    private int count; // 购物车中该商品的数量
    private float amount;
    private int scoreStatus;
    private float score;
    private String comment;
    private String photoUrls;
    private Date createTime;
    private Date updateTime;
    private boolean checked;
    private int userId;


}
