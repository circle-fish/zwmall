package com.cnlbc.dao;

import com.cnlbc.domain.Brand;
import com.cnlbc.domain.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    public List<Cart> findspecial(Cart user);
    public boolean newcart(Cart cart);
    public boolean updatecart(Cart cart);
    public boolean updatecheck(Cart cart);
    public boolean deletecart(@Param("id")int id);
}
