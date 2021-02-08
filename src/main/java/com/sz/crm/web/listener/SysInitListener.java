package com.sz.crm.web.listener;


import com.sz.crm.setting.dao.DicTypeDao;
import com.sz.crm.setting.dao.DicValueDao;
import com.sz.crm.setting.daomain.DicValue;
import com.sz.crm.setting.service.DicService;
import com.sz.crm.setting.service.imp.DicServiceImpl;
import com.sz.crm.utils.ServiceFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("监听器启动");
        ServletContext application = event.getServletContext();
        ApplicationContext ctx= WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

        DicTypeDao bean1 = ctx.getBean(DicTypeDao.class);
        DicValueDao bean2 = ctx.getBean(DicValueDao.class);
        DicService dicService = ctx.getBean(DicService.class);

        Map<String, List<DicValue>> map=dicService.getAll();
        Set<String> set=map.keySet();
        for (String key:set){
            application.setAttribute(key,map.get(key));
        }

       // Stage2Possibility.properties配置文件使用
        ResourceBundle rs=ResourceBundle.getBundle("Stage2Possibility");
        Map<String,String> map1=new HashMap<>();
        Enumeration<String> keys = rs.getKeys();
       while(keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = rs.getString(key);
            map1.put(key,value);
        }
        application.setAttribute("pMap",map1);
        System.out.println("监听器结束");

    }


}
