package com.cnlbc.service;

import com.cnlbc.domain.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface OrderService {
    public Order saveorder( //int id,
                            String orderNo,
                            int userId,
                            int status,
                            float goodsAmount,
                            float expressFee,
                            float totalAmount,
                            float discountAmount,
                            float payAmount,
                            int payMode,
                            int payPlatform,
                            String ipAddress,
                            String paymentNo,
                            String remark,
                            Date createTime,
                            Date updateTime) throws IOException;

    public Order_goods saveordergoods(//int id,
                                        String orderNo,
                                        int goodsId,
                                        int skuId,
                                        String name,
                                        String thumbUrl,
                                        float price,
                                        int count,
                                        float amount,
                                        int scoreStatus,
                                        float score,
                                        String comment,
                                        String photoUrls,
                                        Date createTime,
                                        Date updateTime)throws IOException;

    public Order_address saveorderaddress(
        //int id,
        String orderNo,
        String consignee,
        String phone,
        String province,
        String city,
        String district,
        String address,
        Date updateTime)throws IOException;

    public boolean updateorder(Order order);
    public boolean updateorderstatus(int status, String orderNo) throws IOException;
    public List<Order> OrdersList(int userId) throws IOException;
    public List<Order> DiffOrdersList(int status,int userId) throws IOException;
    public boolean deleteorder(int id);

    public List<Order> test() throws IOException;

    public List<Order_details> OrderdetailsList(String orderNo) throws IOException;

    public List<Order_goods> OrdergoodsList(String orderNo) throws IOException;

    public List<Order_goods> Str2goodsList(String goodlist) throws IOException;

    public Order_pages Orders_page(List<Order> ordersList) throws IOException;

    public List<User_address> getaddressbyid(int UserId) throws IOException;
}
