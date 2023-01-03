package com.cnlbc.dao;

import com.cnlbc.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserMapper {
    public List<User> findall() throws IOException;
    public List<User> findspecial(User user);
    public void save(User user);
    public void updateuser(User user);
    public void deleteuser(User user);
}
