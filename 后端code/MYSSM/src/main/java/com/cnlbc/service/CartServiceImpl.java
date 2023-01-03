package com.cnlbc.service;

import com.cnlbc.dao.CartMapper;
import com.cnlbc.dao.GoodsMapper;
import com.cnlbc.domain.Cart;
import com.cnlbc.domain.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("cartservice")
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Autowired
    private GoodsMapper goodsMapper;
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }


    @Override
    public List<Cart> findUsercart(int userid) {
        try {
            Cart cart = new Cart();
            cart.setUserId(userid);
            return cartMapper.findspecial(cart);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean addcart(int userId, int goodsId, int skuId) {
        try {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setGoodsId(goodsId);
            cart.setSkuId(skuId);
            Goods goods = goodsMapper.details(goodsId);
            if(goods==null)return false;
            cart.setName(goods.getName());
            cart.setThumbUrl(goods.getThumbUrl());
            cart.setPrice(goods.getPrice());
            cart.setCount(1);
            cart.setChecked(1);
            cart.setCreateTime(new Date());
            cart.setUpdateTime(new Date());
            cartMapper.newcart(cart);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean changecartnum(int id,int count) {
        try {
            Cart cart = new Cart();
            cart.setId(id);
            cart.setCount(count);
            cart.setUpdateTime(new Date());
            System.out.println(cart);
            cartMapper.updatecart(cart);
            System.out.println(2);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean changecartcheck(int id,int checked) {
        try {
            Cart cart = new Cart();
            cart.setId(id);
            cart.setChecked(checked);
            cart.setUpdateTime(new Date());
            cartMapper.updatecheck(cart);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deletecart(int id) {
        try {
            cartMapper.deletecart(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}