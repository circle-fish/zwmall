package com.cnlbc.service;

import com.cnlbc.domain.User;
import com.cnlbc.domain.User_address;

import java.io.IOException;
import java.util.List;

public interface User_addressService {
    public List<User_address> getaddress(int UserId) throws IOException;
    public boolean save(int userId, String name, String phone, String province, String city, String district, String address, String label)throws IOException;
    public boolean updateaddress(int id,String name,String phone,String province,String city,String district,String address,String label);
    public boolean deleteaddress(int id);
}
