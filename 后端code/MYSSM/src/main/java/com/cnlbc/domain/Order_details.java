package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Data


public class Order_details {


    private int id;
    private String orderNo;
    private String consignee;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String address;

    private float goodsAmount;
    private float expressFee;
//    private float totalAmount;
    private float discountAmount;
    private float payAmount;
    private int payMode;
    private List<Order_goods> goodsList;
    private Date createTime;


}
