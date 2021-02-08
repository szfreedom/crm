package com.sz.crm.setting.service.imp;
import com.sz.crm.exception.LoginException;
import com.sz.crm.setting.dao.UserDao;
import com.sz.crm.setting.daomain.User;
import com.sz.crm.setting.service.UserService;
import com.sz.crm.utils.DateTimeUtil;
import com.sz.crm.utils.SqlSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public User login(String loginAct, String loginPwd,String ip) throws LoginException {
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);
        if (user==null){
            throw new LoginException("账号密码错误");
        }
        String expireTime=user.getExpireTime();
        String currentTime= DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime)<0){
            throw new LoginException("账号已失效");
        }
        String lockState=user.getLockState();
        if ("0".equals(lockState)){
            throw  new LoginException("账号已锁定");
        }
        String allowIps=user.getAllowIps();
        if (!(allowIps.contains(ip))){
            throw new LoginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList=userDao.getUserList();

        return userList;
    }
}
