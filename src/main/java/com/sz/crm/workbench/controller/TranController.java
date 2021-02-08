package com.sz.crm.workbench.controller;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.setting.service.UserService;
import com.sz.crm.utils.DateTimeUtil;
import com.sz.crm.utils.UUIDUtil;
import com.sz.crm.workbench.daomain.Customer;
import com.sz.crm.workbench.daomain.Tran;
import com.sz.crm.workbench.service.CustomerService;
import com.sz.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Autowired
    private TranService tranService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @RequestMapping("/add.do")
    public ModelAndView add(){
        ModelAndView mv=new ModelAndView();
        List<User> userList=userService.getUserList();
        mv.addObject("uList",userList);
        mv.setViewName("forward:/workbench/transaction/save.jsp");
        return mv;
    }
    @RequestMapping("/getCustomerName.do")
    @ResponseBody
    public Object getCustomerName(String name){
        List<String> sList=customerService.getCustomerName(name);
        return sList;
    }
    @RequestMapping("/save.do")
    @ResponseBody
    public Object save(Tran tran, String customerName, HttpServletRequest request){

        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag=tranService.save(tran,customerName);
        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        return map;

    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
        Tran tran=tranService.detail(id);
        mv.addObject("t",tran);
        mv.setViewName("forward:/workbench/transaction/detail.jsp");
        return mv;
    }

    @RequestMapping("/changeStage.do")
    @ResponseBody
    public Object changeStage(Tran tran,HttpServletRequest request){
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();
        tran.setEditTime(editTime);
        tran.setEditBy(editBy);
        boolean flag=tranService.changeStage(tran);
        Tran tran1 = tranService.detail(tran.getId());
        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("t",tran1);
        return map;

    }
    @RequestMapping("/getCharts.do")
    @ResponseBody
    public Object getCharts(){
        int total=tranService.getTotal();

        List<Map<String,Object>> dataList=tranService.getDataList();
        Map<String,Object> map=new HashMap<>();
        map.put("total",total);
        map.put("dataList",dataList);
        return map;
    }
}
