package com.cnlbc.service;

import com.cnlbc.dao.UserMapper;
import com.cnlbc.dao.User_addressMapper;
import com.cnlbc.domain.User;
import com.cnlbc.domain.User_address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("user_address_service")
public class User_addressServiceImpl implements User_addressService {

    @Autowired
    private User_addressMapper user_addressMapper;
    public void setUser_addressMapper(User_addressMapper user_addressMapper) {
        this.user_addressMapper = user_addressMapper;
    }

    @Override
    public List<User_address> getaddress(int UserId) throws IOException {
        try{
            List<User_address> user_addressList = user_addressMapper.findspecial(UserId);
            System.out.println(user_addressList);
            return  user_addressList;
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean save(int userId, String name, String phone, String province, String city, String district, String address, String label) throws IOException {
        try{
            User_address user_address = new User_address();
            user_address.setUserId(userId);
            user_address.setName(name);
            user_address.setPhone(phone);
            user_address.setProvince(province);
            user_address.setCity(city);
            user_address.setDistrict(district);
            user_address.setAddress(address);
            user_address.setLabel(label);
            user_address.setCreateTime(new Date());
            user_address.setUpdateTime(new Date());
            user_addressMapper.save(user_address);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateaddress(int id, String name, String phone, String province, String city, String district, String address, String label) {
        try{
            User_address user_address = new User_address();
            user_address.setId(id);
            user_address.setName(name);
            user_address.setPhone(phone);
            user_address.setProvince(province);
            user_address.setCity(city);
            user_address.setDistrict(district);
            user_address.setAddress(address);
            user_address.setLabel(label);
            user_address.setUpdateTime(new Date());
            user_addressMapper.updateaddress(user_address);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteaddress(int id) {
        try{
            user_addressMapper.deleteaddress(id);
            return  true;
        }
        catch (Exception e){
            return false;
        }
    }
}