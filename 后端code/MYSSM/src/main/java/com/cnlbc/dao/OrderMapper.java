package com.cnlbc.dao;

import com.cnlbc.domain.*;
import org.apache.ibatis.annotations.Select;

import java.io.IOException;
import java.util.List;

public interface OrderMapper {
    public void saveorder(Order order);
    public void saveordergoods(Order_goods order_goods);
    public void saveorderaddress(Order_address order_address);
    public void updateorder(Order order);

    //public void updateorderstatus(Order order);

    public void deleteorder(int id);
    public List<Order> OrdersList(int userId);

    public List<Order> DiffOrdersList(int status,int userId);
    public List<Order> test();
//@Select("select consignee,phone,province,city,district,address,goodsAmount,expressFee,discountAmount,payAmount,payMode from `order`,order_address where `order`.orderNo= '1' and `order_address`.orderNo= '1';")
    public List<Order_details> OrderdetailsList(String orderNo);

    public List<Order_goods> OrdergoodsList(String orderNo);

    public List<User_address> getaddressbyid(int Id) ;

}
