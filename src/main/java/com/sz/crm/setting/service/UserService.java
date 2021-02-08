package com.sz.crm.setting.service;

import com.sz.crm.exception.LoginException;
import com.sz.crm.setting.daomain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd,String ip) throws LoginException;

    List<User> getUserList();
}
