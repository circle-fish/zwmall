package com.cnlbc.service;

import com.cnlbc.domain.Cart;

import java.io.IOException;
import java.util.List;

public interface CartService {
    public List<Cart> findUsercart(int userid);//返回客户的购物车
    boolean addcart(int userId, int goodsId, int skuId);//添加新的购物车

    public boolean changecartnum(int id,int count);//修改购买数量
    public boolean changecartcheck(int id,int checked);//修改选中状态
    public boolean deletecart(int id);//删除购物车
}
