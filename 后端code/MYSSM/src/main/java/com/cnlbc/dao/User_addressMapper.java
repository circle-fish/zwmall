package com.cnlbc.dao;

import com.cnlbc.domain.User;
import com.cnlbc.domain.User_address;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

public interface User_addressMapper {
    public List<User_address> findspecial(@Param("userId")int userId);
    public boolean save(User_address user_address);
    public boolean updateaddress(User_address user_address);
    public boolean deleteaddress(@Param("id")int id);
}
