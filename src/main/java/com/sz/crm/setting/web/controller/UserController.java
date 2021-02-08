package com.sz.crm.setting.web.controller;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.setting.service.UserService;
import com.sz.crm.utils.MD5Util;
import com.sz.crm.utils.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController  {
    @Autowired
    private UserService userService;

    @RequestMapping("/setting/user/login.do")
    @ResponseBody
    private Object login(HttpServletRequest request, String loginAct, String loginPwd) {
        loginPwd= MD5Util.getMD5(loginPwd);
        String ip=request.getRemoteAddr();
        System.out.println(ip);
        try{
            User user=userService.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            Map<String,Boolean> map = new HashMap<String,Boolean>();
            map.put("success",true);
            return map;
        }catch (Exception e){
            e.printStackTrace();
                //{"success":true,"msg":?}
            String msg=e.getMessage();
            Map<String,Object> map=new HashMap<>();
            map.put("success",false);
            map.put("msg",msg);
            return map;
        }
    }
}
