package com.sz.crm.workbench.controller;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.utils.DateTimeUtil;
import com.sz.crm.utils.UUIDUtil;
import com.sz.crm.vo.PagitionVO;
import com.sz.crm.workbench.daomain.Activity;
import com.sz.crm.workbench.daomain.Clue;
import com.sz.crm.workbench.daomain.Tran;
import com.sz.crm.workbench.service.ActivityService;
import com.sz.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService;

    @Autowired
    private ActivityService activityService;


    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Object getUserList(){
        List<User> userList= clueService.getUserList();
        return userList;
    }

    @RequestMapping(value = "/save.do",method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request, Clue clue){
        clue.setId(UUIDUtil.getUUID());

        clue.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag=clueService.save(clue);
        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        return map;
    }

    @RequestMapping("/pageList.do")
    @ResponseBody
    public PagitionVO pageList(Clue clue,
                           @RequestParam(value="pageNo") String pageNostr,
                           @RequestParam(value="pageSize")String pageSizestr){
        int pageNo=Integer.valueOf(pageNostr);
        int pageSize=Integer.valueOf(pageSizestr);
        int start=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("clue",clue);
        map.put("start",start);
        map.put("pageSize",pageSize);
        PagitionVO vo=clueService.pageList(map);

        return vo;
    }

    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv=new ModelAndView();
      Clue clue=clueService.detail(id);
      mv.addObject("c",clue);
      mv.setViewName("forward:/workbench/clue/detail.jsp");
      return mv;
    }

    @RequestMapping("/getActivityListByClueId.do")
    @ResponseBody
    public Object getActivityListByClueId(String clueId){
        List<Activity> activityList=activityService.getActivityListByClueId(clueId);
        return activityList;
    }

    @RequestMapping("/unbandActivity.do")
    @ResponseBody
    public Object unbandActivity(String id){
           boolean flag= clueService.unbandActivity(id);
           Map<String,Object> map=new HashMap<>();
           map.put("success",flag);
           return map;
    }
    @RequestMapping("/getActivityByClueIdAndByName.do")
    @ResponseBody
    public Object getActivityByClueIdAndByName(String id,String aname) {
        Map<String, Object> map=new HashMap<>();
        map.put("id",id);
        map.put("aname",aname);
        List<Activity> activityList=clueService.getActivityByClueIdAndByName(map);
        return activityList;
    }

    @RequestMapping("/band.do")
    @ResponseBody
    public Object band(String cid,String[] aid) {
        boolean flag=clueService.band(cid,aid);
        return flag;
    }

    @RequestMapping("/getActivityByName.do")
    @ResponseBody
    public Object getActivityByName(String name) {
        List<Activity> activityList=activityService.getActivityByName(name);
        return activityList;
    }

    @RequestMapping("/convert.do")
    @ResponseBody
    public ModelAndView convert(String clueId,String flag,Tran tran,HttpServletRequest request) {
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
                ModelAndView mv=new ModelAndView();

                if ("a".equals(flag)){
                    tran.setId(UUIDUtil.getUUID());
                    tran.setCreateBy(createBy);
                    tran.setCreateTime(DateTimeUtil.getSysTime());
                }
                boolean flag1=clueService.convert(clueId,tran,createBy,flag);
                if (flag1){
                    mv.setViewName("redirect:/workbench/clue/index.jsp");
                    return mv;
                }else{


                return mv;}

    }

}
