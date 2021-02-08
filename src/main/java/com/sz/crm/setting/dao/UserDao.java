package com.sz.crm.setting.dao;

import com.sz.crm.setting.daomain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, Object> map);

    List<User> getUserList();
}
