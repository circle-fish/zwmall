package com.cnlbc.service;

import com.cnlbc.dao.UserMapper;
import com.cnlbc.domain.MultipartFileDto;
import com.cnlbc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
@Service("userservice")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Override
    public List<User> findAllUser() throws IOException {
        List<User> userList = userMapper.findall();
        System.out.println(userList);
        return userList;
    }

    public User enter(String user, String password) throws IOException {
        try{
            //设置查询条件
            User use = new User();
            use.setUsername(user);
            use.setPassword(password);
            System.out.println(use);
            List<User> userList = userMapper.findspecial(use);
            System.out.println(userList);
            if(userList.size()==1){//找到唯一用户
                return userList.get(0);
            }
            return null;
        }
        catch (Exception e){
            return null;
        }

    }

    public User save(String username,String password, String name,String phone, String email){
        try{
            User use = new User();
            use.setUsername(username);
            if((userMapper.findspecial(use)).size()>0)return null;
            use.setPassword(password);
            use.setName(name);
            use.setPhone(phone);
            use.setEmail(email);
            userMapper.save(use);
            return enter(username,password);
        }
        catch (Exception e){
            return null;
        }
    }


    public boolean updatepassword(int id,String password){
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return updateuser(user);
    }

    public boolean updatephone(int id,String phone){
        User user = new User();
        user.setId(id);
        user.setPhone(phone);
        return updateuser(user);
    }
    public boolean updateemail(int id,String email){
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        return updateuser(user);
    }
    public boolean updateself(int id,String name,String sex,String birth){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSex(sex);
        user.setBirth(birth);
        return updateuser(user);
    }

    public boolean updateuser(User user){
        try{
            userMapper.updateuser(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @RequestMapping("/upload")
    public String upload(@RequestParam MultipartFile upfile,@RequestParam String uname, @RequestParam String password) throws IOException {
        //获取表单中提交的其他参数
        System.out.println(uname);
        User user = enter(uname,password);
        System.out.println("????");
        if(upfile.isEmpty()|| user == null)
            return null;
        //获取文件的名称
        String fName = upfile.getOriginalFilename();
        //保存图片的目录
        String path = "D:/upload";
        File file = new File(path);
        //如果目录不存在，创建
        if(!file.exists()){
            file.mkdir();
        }
        File f = new File(path, fName);
        System.out.println(fName);
        try {
            //上传文件
            upfile.transferTo(f);
            user.setImage(path+fName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/success.jsp";
    }

}