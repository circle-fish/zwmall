package com.cnlbc.domain;

import lombok.Data;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
@Repository("user")
public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String sex;
    private String Image;
    private String birth;

}