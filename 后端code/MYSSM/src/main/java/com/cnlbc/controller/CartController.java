package com.cnlbc.controller;
import com.cnlbc.domain.Cart;
import com.cnlbc.domain.Goods;
import com.cnlbc.domain.Result;
import com.cnlbc.service.CartService;
import com.cnlbc.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Controller
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    @Qualifier("cartservice")
    private CartService cartService;
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping("/findcart")
    public Result findUsercart(@RequestParam("token")int userid) throws IOException{
        System.out.println(userid);
        List<Cart> cartList = cartService.findUsercart(userid);
        System.out.println(cartList);
        if(cartList!=null){
            return Result.success("找到购物车",cartList);
        }
        return Result.error("401","未查询到用户购物车");
    }

    @RequestMapping("/addcart")
    public Result addcart(@RequestParam("token")int userId, @RequestParam("goodsid")int goodsId,@RequestParam("skuid") int skuId) throws IOException{
        System.out.println(userId);
        if(cartService.addcart(userId, goodsId, skuId)){
            System.out.println("addok");
            return Result.success("找到购物车");
        }
        return Result.error("401","未查询到用户购物车");
    }

    @RequestMapping("/changecount")
    public Result changecount(@RequestParam("id")int id, @RequestParam("count")int count) throws IOException{
        System.out.println(id);
        System.out.println(count);
        if(cartService.changecartnum(id, count)){
            System.out.println("count,ok");
            return Result.success("购物数量改变成功");
        }
        return Result.error("401","购物数量改变失败");
    }
    @RequestMapping("/changecheck")
    public Result changecheck(@RequestParam("id")int id, @RequestParam("checked")boolean check) throws IOException{
        int checked = check?1:0;
        if(cartService.changecartcheck(id, checked)){
            System.out.println("check,ok");
            return Result.success("找到购物车");
        }
        return Result.error("401","未查询到用户购物车");
    }

    @RequestMapping("/deletecart")
    public Result changecheck(@RequestParam("id")int id) throws IOException{
        if(cartService.deletecart(id)){
            System.out.println("delete,ok");
            return Result.success("购物车删除成功");
        }
        return Result.error("401","购物车删除失败");
    }
}