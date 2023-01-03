package com.cnlbc.service;

import com.cnlbc.domain.MultipartFileDto;
import com.cnlbc.domain.User;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface UserService {
    public List<User> findAllUser() throws IOException;
    public User enter(String user, String password) throws IOException;
    public User save(String username,String password, String name,String phone, String email)throws IOException;

    public boolean updateuser(User user);
    public boolean updatepassword(int id,String password);
    public boolean updatephone(int id,String phone);
    public boolean updateemail(int id,String email);
    public boolean updateself(int id,String name,String sex,String birth);
    public String upload(@RequestParam MultipartFile upfile, @RequestParam String uname, @RequestParam String password) throws IOException;

}
