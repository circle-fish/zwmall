package com.cnlbc.controller;
import com.cnlbc.domain.Result;
import com.cnlbc.domain.User;
import com.cnlbc.domain.User_address;
import com.cnlbc.service.UserService;
import com.cnlbc.service.User_addressService;
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
@RequestMapping("/user_address")
public class User_addressController {

    @Autowired
    @Qualifier("user_address_service")
    private User_addressService user_addressService;
    public void setUser_addressService(User_addressService user_addressService) {
        this.user_addressService = user_addressService;
    }
    @RequestMapping("/getaddress")
    public Result getaddress(@RequestParam("userId")int UserId) throws IOException {
        List<User_address> user_addressList = user_addressService.getaddress(UserId);
        if(user_addressList==null){//未查询到该用户
            return Result.error("401","未查询到收获地址");
        }
        System.out.println("ok");
        return Result.success("查询成功",user_addressList);
    }

    @RequestMapping("/addaddress")
    public Result addaddress(@RequestParam("userId")int userId, @RequestParam("name")String name,@RequestParam("phone") String phone,
                             @RequestParam("province")String province, @RequestParam("city")String city, @RequestParam("district")String district,
                             @RequestParam("address")String address, @RequestParam("label")String label) throws IOException {
        System.out.println(address);
        if(!user_addressService.save(userId, name, phone, province,city,district, address,label)){//用户创建失败
            System.out.println("address,ok");
            return Result.error("401","地址添加失败");
        }
        return Result.success("地址添加成功");
    }

    @RequestMapping("/updateaddress")
    public Result updateaddress(@RequestParam("id")int id, @RequestParam("name")String name,@RequestParam("phone") String phone,
                             @RequestParam("province")String province, @RequestParam("city")String city, @RequestParam("district")String district,
                             @RequestParam("address")String address, @RequestParam("label")String label) throws IOException {
        if(!user_addressService.updateaddress(id, name, phone, province,city,district, address,label)){//用户创建失败
            return Result.error("401","地址更改失败");
        }
        return Result.success("地址更改成功");
    }

    @RequestMapping("/deleteaddress")
    public Result deleteaddress(@RequestParam("id")int id) throws IOException {
        if(!user_addressService.deleteaddress(id)){//用户创建失败
            return Result.error("401","地址删除失败");
        }
        return Result.success("地址删除成功");
    }
}