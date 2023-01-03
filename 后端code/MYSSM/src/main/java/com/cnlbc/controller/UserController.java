package com.cnlbc.controller;
import com.cnlbc.domain.Result;
import com.cnlbc.domain.User;
import com.cnlbc.service.UserService;
import com.cnlbc.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("userservice")
    private UserService userService;
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/enter")
    public Result enter(@RequestParam("username")String username, @RequestParam("password")String password) throws IOException {
        System.out.println(username);
        System.out.println(password);
        User user = userService.enter(username,password);
        System.out.println(user);
        if(user==null){//未查询到该用户
            return Result.error("401","未查询到该用户");
        }
        return Result.success("登录成功",user);
    }

    @RequestMapping("/register")
    public Result register(@RequestParam("username")String username, @RequestParam("password")String password,@RequestParam("name")String name,@RequestParam("phone")String phone,@RequestParam("email")String email) throws IOException {
        User user = userService.save(username,password,name,phone,email);
        if(user == null){//用户创建失败
            return Result.error("401","用户注册失败");
        }
        return Result.success("用户注册成功",user);
    }

    @RequestMapping("/updatepassword")
    public Result updatepassword(@RequestParam("id")int id,@RequestParam("password")String password) throws IOException {
        if(userService.updatepassword(id,password)){
            return Result.success("用户修改成功");
        }
        return Result.error("401","用户修改失败");
    }
    @RequestMapping("/updateemail")
    public Result updateemail(@RequestParam("id")int id,@RequestParam("email")String email) throws IOException {
        if(userService.updateemail(id,email)){
            return Result.success("用户修改成功");
        }
        return Result.error("401","用户修改失败");
    }
    @RequestMapping("/updatephone")
    public Result updatephone(@RequestParam("id")int id,@RequestParam("phone")String phone) throws IOException {
        if(userService.updatephone(id,phone)){
            return Result.success("用户修改成功");
        }
        return Result.error("401","用户修改失败");
    }
    @RequestMapping("/updateself")
    public Result updateself(@RequestParam("id")int id,@RequestParam("name")String name,@RequestParam("sex")String sex,@RequestParam("birth") String birth) throws IOException {
        if(userService.updateself(id,name,sex,birth)){
            return Result.success("用户修改成功");
        }
        return Result.error("401","用户修改失败");
    }
    @PostMapping ("/upload")
    public Result upload(@RequestParam("upfile") MultipartFile upfile)
            //,@RequestParam("uname")String uname,@RequestParam("password")String password) throws IOException
    {
        System.out.println("ASDAsd");
        //String res = userService.upload(upfile,uname,password);
        //if(res == null)
        //    return Result.error("401","用户头像上传失败");
        //return Result.success(res);
        return Result.success("6666");
    }

}