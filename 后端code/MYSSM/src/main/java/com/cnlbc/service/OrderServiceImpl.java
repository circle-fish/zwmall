package com.cnlbc.service;

import com.cnlbc.dao.OrderMapper;
import com.cnlbc.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
//import java.util.Date;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Service("orderservice")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public Order saveorder(//int id,
                           String orderNo,
                           int userId,
                           int status,//=0
                           float goodsAmount,
                           float expressFee,//=18
                           float totalAmount,//=goodsAmount
                           float discountAmount,//=0
                           float payAmount,//=goodsAmount
                           int payMode,//NULL
                           int payPlatform,//NULL
                           String ipAddress,//=127.0.0.1
                           String paymentNo,//=123456789
                           String remark,//='.'
                           Date createTime,//=datetime.now();
                           Date updateTime)
    {
        try{
            Order order = new Order();

            //order.setId(id);
            order.setOrderNo(orderNo);
            order.setUserId(userId);
            order.setStatus(status);
            order.setGoodsAmount(goodsAmount);
            order.setExpressFee(expressFee);
            order.setTotalAmount(totalAmount);
            order.setDiscountAmount(discountAmount);
            order.setPayAmount(payAmount);
            order.setPayMode(payMode);
            order.setPayPlatform(payPlatform);
            order.setIpAddress(ipAddress);
            order.setPaymentNo(paymentNo);
            order.setRemark(remark);
            order.setCreateTime(createTime);
            order.setUpdateTime(updateTime);
            //System.out.println(order);
            orderMapper.saveorder(order);
            return order;
        }
        catch (Exception e){
            return null;
        }
    }


    public Order_address saveorderaddress(
            //int id,
            String orderNo,
            String consignee,
            String phone,
            String province,
            String city,
            String district,
            String address,
            Date updateTime)
    {
        try{
            Order_address order_address = new Order_address();

            //order.setId(id);
            order_address.setOrderNo(orderNo);
            order_address.setConsignee(consignee);
            order_address.setPhone(phone);
            order_address.setProvince(province);
            order_address.setCity(city);
            order_address.setDistrict(district);
            order_address.setAddress(address);
            order_address.setUpdateTime(updateTime);
            orderMapper.saveorderaddress(order_address);
            return order_address;
        }
        catch (Exception e){
            return null;
        }
    }

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
                               Date updateTime)
    {
        try{
            Order_goods order_goods=new Order_goods();

            //order.setId(id);
            order_goods.setOrderNo(orderNo);
            order_goods.setGoodsId(goodsId);
            order_goods.setSkuId(skuId);
            order_goods.setName(name);
            order_goods.setThumbUrl(thumbUrl);
            order_goods.setPrice(price);
            order_goods.setCount(count);
            order_goods.setAmount(amount);
            order_goods.setScoreStatus(scoreStatus);
            order_goods.setScore(score);
            order_goods.setComment(comment);
            order_goods.setPhotoUrls(photoUrls);
            order_goods.setCreateTime(createTime);
            order_goods.setUpdateTime(updateTime);
            orderMapper.saveordergoods(order_goods);
            return order_goods;
        }
        catch (Exception e){
            return null;
        }
    }


    public boolean updateorder(Order order){
        try{
            orderMapper.updateorder(order);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean deleteorder(int id){
        try{
            orderMapper.deleteorder(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean updateorderstatus(int status, String orderNo)
    {
            Order order = new Order();

            order.setStatus(status);
            Date updateTime= new Date(System.currentTimeMillis());
            order.setUpdateTime(updateTime);
            return updateorder(order);
    }

    public List<Order> OrdersList(int userId) throws IOException
    {
        try{
            List<Order> OrdersList = orderMapper.OrdersList(userId);
            return OrdersList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<User_address> getaddressbyid(int Id) throws IOException{
        try{
            List<User_address> user_addressesList = orderMapper.getaddressbyid(Id);
            return user_addressesList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order> DiffOrdersList(int status,int userId) throws IOException
    {
        //System.out.println(status + "?");
        try{
            List<Order> OrdersList = orderMapper.DiffOrdersList(status,userId);
            return OrdersList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order> test() throws IOException{
        List<Order> testList = orderMapper.test();
        //System.out.println(testList);
        return testList;
    }

    public List<Order_details> OrderdetailsList(String orderNo) throws IOException
    {
        try{
            List<Order_details> OrderdetailsList = orderMapper.OrderdetailsList(orderNo);
            List<Order_goods> OrdergoodsList = orderMapper.OrdergoodsList(orderNo);

            for (Order_details order_details:OrderdetailsList) {
                order_details.setGoodsList(OrdergoodsList);
            }

            return OrderdetailsList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order_goods> OrdergoodsList(String orderNo) throws IOException
    {
        try{
            List<Order_goods> OrdergoodsList = orderMapper.OrdergoodsList(orderNo);
            return OrdergoodsList ;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Order_goods> Str2goodsList(String goodlist)throws IOException{
        try {
            List<Order_goods> goodsList = new ArrayList<>();
            Order_goods order_good = new Order_goods();
            List<String> vals = new ArrayList<>();
            String jsonStr = goodlist;
            String infos[] = jsonStr.substring(1, jsonStr.length() - 1).split("}");
            for (String info : infos) {
                if (info.length() != 0) {
                    String goodinfos[] = info.substring(1).split(",\"");
                    //System.out.println("info==" + info);
                    for (String good : goodinfos) {

                        String keyval[] = good.split("\":");

                        for (int i = 0; i < keyval.length; i++) {
                            if (i % 2 != 0) {
                                if (keyval[i].startsWith("\"")) {
                                    vals.add(keyval[i].substring(1, keyval[i].length() - 1));
                                    //System.out.println(keyval[i].substring(1,keyval[i].length()-1));
                                } else {
                                    vals.add(keyval[i]);
                                    //System.out.println(keyval[i]);
                                }
                            }
                        }
                    }
                }
                order_good.setId(Integer.parseInt(vals.get(0)));
                order_good.setUserId(Integer.parseInt(vals.get(1)));
                order_good.setGoodsId(Integer.parseInt(vals.get(2)));
                order_good.setSkuId(Integer.parseInt(vals.get(3)));
                order_good.setName(vals.get(4));
                order_good.setThumbUrl(vals.get(5));
                order_good.setPrice(Float.parseFloat(vals.get(6)));
                order_good.setCount(Integer.parseInt(vals.get(7)));
                order_good.setChecked(Boolean.parseBoolean(vals.get(8)));
                order_good.setAmount(Float.parseFloat(vals.get(6)) * Integer.parseInt(vals.get(7)));
//            System.out.println(vals.get(9));
//            order_good.setCreateTime(Date.valueOf(vals.get(9)));
//            order_good.setUpdateTime(Date.valueOf(vals.get(10)));
                goodsList.add(order_good);
            }
            //System.out.println(goodsList);
            return goodsList ;

        }
        catch (Exception e){
            return null;
        }
    }

    public Order_pages Orders_page(List<Order> ordersList) throws IOException
    {
        try{
            Order_pages orders_page = new Order_pages();
            int pagesetting =3;
            int page = (ordersList.size()+pagesetting-1)/pagesetting;

            orders_page.setOrdersList(ordersList);
            orders_page.setPage(page);

            return orders_page;
        }
        catch (Exception e){
            return null;
        }
    }


}