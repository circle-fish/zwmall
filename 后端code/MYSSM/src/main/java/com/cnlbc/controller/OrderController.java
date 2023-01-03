package com.cnlbc.controller;
import com.cnlbc.domain.*;
import com.cnlbc.domain.User;
import com.cnlbc.domain.User_address;
import com.cnlbc.domain.Order_address;
import com.cnlbc.service.UserService;
import com.cnlbc.service.User_addressService;
import com.cnlbc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/order")


public class OrderController {
    @Autowired
    @Qualifier("orderservice")
    private OrderService orderService;
    public void setOrderService(OrderService orderService){
        this.orderService = orderService;
    }

    @RequestMapping("/save")
    public Result saveorder(
            @RequestParam("userId")int userId,
            @RequestParam("totalAmount")float totalAmount,
            @RequestParam("address")int addressID,
            @RequestParam("goodsList")String goodsList
            //@RequestBody List<Order_goods> goodsList
            //@RequestBody Map goodsList
    ) throws IOException
    {

        long datetime1=new Date(System.currentTimeMillis()).getTime();
        String orderNo=userId+Long.toString(datetime1);
        String ipAddress="127.0.0.1";
        String paymentNo="123456789";
        String remark="/";
        Date createTime= new Date(System.currentTimeMillis());
        Date updateTime= createTime;
        Order order = orderService.saveorder(orderNo,
                userId, 0, totalAmount, 18, totalAmount, 0, totalAmount+18, -1, -1, ipAddress, paymentNo, remark, createTime, updateTime);
        List<Order_goods> OrdergoodsList=orderService.Str2goodsList(goodsList);
        for (Order_goods order_goods:OrdergoodsList) {
            orderService.saveordergoods(orderNo,order_goods.getGoodsId(),order_goods.getSkuId(),order_goods.getName(),order_goods.getThumbUrl(),order_goods.getPrice(),order_goods.getCount(),
                    order_goods.getPrice()*order_goods.getCount(),0,5,"Comment","www.chenhuan.jpg",createTime,updateTime);
        }
        //System.out.println(goodsList);
        User_address user_address = orderService.getaddressbyid(addressID).get(0);

        Order_address order_address = orderService.saveorderaddress(orderNo,
                 user_address.getName(), user_address.getPhone(), user_address.getProvince(), user_address.getCity(), user_address.getDistrict(), user_address.getAddress(),updateTime);
        if(order == null){//订单创建失败
            return Result.error("401","订单创建失败");
        }
        return Result.success("订单创建成功",orderNo);
    }


    @RequestMapping("/orders")
    public Result OrdersList(@RequestParam("statusIndex")int statusIndex,@RequestParam("userId")int userId) throws IOException {
        List<Order> OrdersList = null;
        if (statusIndex == 0) {
            OrdersList = orderService.OrdersList(userId);
            if (OrdersList != null) {
                //return Result.success("查找到订单", orderService.Orders_page(OrdersList));
                return Result.success("查找到订单", OrdersList);
            }
            return Result.error("401", "未查找到订单");
        } else if (statusIndex == 1) {
            OrdersList = orderService.DiffOrdersList(0, userId);
        } else if (statusIndex == 2) {
            OrdersList = orderService.DiffOrdersList(2, userId);
        } else if (statusIndex == 3) {
            OrdersList = orderService.DiffOrdersList(8, userId);
        } else if (statusIndex == 4) {
            OrdersList = orderService.DiffOrdersList(40, userId);
        } else {
            return Result.error("401", "未查找到订单");
        }

        if(OrdersList!=null){
            //return Result.success("查找到订单!",orderService.Orders_page(OrdersList));
            return Result.success("查找到订单!",OrdersList);
        }
        return Result.error("401","未查找到订单");
    }



    @RequestMapping("/updatestatus")
    public Result updatestatus(@RequestParam("status")int status,@RequestParam("orderNo")String orderNo) throws IOException {
        if(orderService.updateorderstatus(status,orderNo)){
            return Result.error("401","订单更新失败");
        }
        return Result.success("订单更新成功");
    }

    @RequestMapping("/orderdetails")
    public Result OrderdetailsList(@RequestParam("orderNo")String orderNo) throws IOException {
        List<Order_details> OrderdetailsList = null;
        OrderdetailsList = orderService.OrderdetailsList(orderNo);
        if (OrderdetailsList != null) {
            return Result.success("查找到订单", OrderdetailsList);
        }
        return Result.error("401", "未查找到订单");
    }

    @RequestMapping("/deleteorder")
    public Result deleteorder(@RequestParam("id")int id) throws IOException {
        if(orderService.deleteorder(id)){
            return Result.error("401","订单删除失败");
        }
        return Result.success("订单删除成功");
    }

    @RequestMapping("/test")
    public Result test() throws IOException {
        List<Order> OrdersList = orderService.test();
        if(OrdersList!=null){
            return Result.success("查找到订单",OrdersList);
        }
        return Result.error("401","未查找到订单");
    }
    @RequestMapping("/submit")
    public Result submit() throws IOException {
        String orderNo="1";
        return Result.success("orderNo=",orderNo);
    }

    @RequestMapping("/pay/details")
    public Result Orderdetails(@RequestParam("orderNo")String orderNo) throws IOException {
        List<Order_details> OrderdetailsList = null;
        OrderdetailsList = orderService.OrderdetailsList(orderNo);

        if (OrderdetailsList != null) {
            return Result.success("查找到订单", OrderdetailsList.get(0));
        }
        return Result.error("401", "未查找到订单");
    }

}
