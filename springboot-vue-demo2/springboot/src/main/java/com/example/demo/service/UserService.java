package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.User;


public interface UserService {
    void save(User user);

    Page<User> list(Integer pageNum, Integer pageSize, String search);

    void edit(User user);

    void delete(Integer id);
}
