package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Data
@Repository("order_express")

public class Order_express {

    //Table structure for order_express
    private int id;
    private String orderNo;
    private String name;
    private String linkUrl;
    private String waybillNo;
    private float packagingScore;
    private float speedScore;
    private String comment;
    private int scoreStatus;
    private int adminId;
    private Date createTime;
    private Date updateTime;

}
