package com.cnlbc.domain;

import lombok.Data;

import java.util.List;

@Data


public class Order_pages {

    //private Order_pages Orders_page;
    private List<Order> ordersList;
    private int page;


}
