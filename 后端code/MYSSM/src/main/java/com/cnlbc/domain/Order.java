package com.cnlbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Data
@Repository("order")

public class Order {

    //Table structure for order
    private int id;
    private String orderNo;
    private int userId;
    private int status;
    private float goodsAmount;
    private float expressFee;
    private float totalAmount;
    private float discountAmount;
    private float payAmount;
    private int payMode;
    private int payPlatform;
    private String ipAddress;
    private String paymentNo;
    private String remark;
    private Date createTime;
    private Date updateTime;

//    //Table structure for order_address
//    private String consignee;
//    private String phone;
//    private String province;
//    private String city;
//    private String district;
//    private String address;
//
//    //Table structure for order_express
//    private String name;
//    private String linkUrl;
//    private String waybillNo;
//    private float packagingScore;
//    private float speedScore;
//    private String comment;
//    private int scoreStatus;
//    private int adminId;
//
//    //Table structure for order_goods
//    private int goodsId;
//    private int skuId;
//    private String thumbUrl;
//    private float price;
//    private int count;
//    private float amount;
//    private float score;
//    private String photoUrls;
//
//    //Table structure for order_time
//    private String avatarUrl;
//    private String email;
//    private String password;
//    private String salt;
//
//    private int securityLevel;
//    private int gender;
//    private int count;
//    private int count;
//    private int count;
//
//
//    private Date createTime;

}
