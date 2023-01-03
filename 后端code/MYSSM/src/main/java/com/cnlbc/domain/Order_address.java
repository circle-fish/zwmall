package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Data
@Repository("order_address")

public class Order_address {

    //Table structure for order_address
    private int id;
    private String orderNo;
    private String consignee;
    private String phone;
    private String province;
    private String city;
    private String district;
    private String address;

    private Date updateTime;

}
