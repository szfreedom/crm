package com.sz.crm.workbench.controller;


import com.sun.org.apache.regexp.internal.RE;
import com.sz.crm.setting.daomain.User;
import com.sz.crm.setting.service.UserService;
import com.sz.crm.setting.service.imp.UserServiceImp;
import com.sz.crm.utils.*;
import com.sz.crm.vo.PagitionVO;
import com.sz.crm.workbench.daomain.Activity;
import com.sz.crm.workbench.daomain.ActivityRemark;
import com.sz.crm.workbench.service.ActivityService;
import com.sz.crm.workbench.service.imp.ActivityServiceImp;
import sun.dc.pr.PRError;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
                getUserList(request,response);
        }else if("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }
        else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }
        else if("/workbench/activity/getUserListAndActivity.do".equals(path)){
            getUserListAndActivity(request,response);
        }
        else if("/workbench/activity/update.do".equals(path)){
            update(request,response);
        }
        else if("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }
        else if("/workbench/activity/getRemarkListByAid.do".equals(path)){
            getRemarkListByAid(request,response);
        }
        else if("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }
        else if("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if("/workbench/activity/editRemark.do".equals(path)){
            editRemark(request,response);
        }
        else if("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }

    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String noteContent = request.getParameter("noteContent");
        String id = request.getParameter("id");
        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setEditBy(editBy);
        activityRemark.setEditTime(editTime);
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditFlag(editFlag);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag=as.updateRemark(activityRemark);
        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("u",activityRemark);
        PrintJson.printJsonObj(response,map);


    }

    private void editRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        String noteContent=as.editRemark(id);
        PrintJson.printJsonObj(response,noteContent);
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        String noteContent = request.getParameter("noteContent");
        String activityId = request.getParameter("activityId");
        String id=UUIDUtil.getUUID();
        String createTime= DateTimeUtil.getSysTime();
        String createBy=((User)request.getSession().getAttribute("user")).getName();
        String editFlag="0";
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        ActivityRemark activityRemark=new ActivityRemark();
        activityRemark.setActivityId(activityId);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setId(id);
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(createTime);
        activityRemark.setEditFlag(editFlag);

        boolean flag=as.saveRemark(activityRemark);

        Map<String,Object> map=new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        PrintJson.printJsonObj(response,map);
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) {
        String activityId = request.getParameter("activityId");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        List<ActivityRemark> activityRemarkList=as.getRemarkListByAid(activityId);
        PrintJson.printJsonObj(response,activityRemarkList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());

        Activity activity=as.detail(id);
        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        Activity activity=new Activity();
        reflectutils.makeRequestToObject(request,activity);
        String editTime= DateTimeUtil.getSysTime();
        String editBy=((User)request.getSession().getAttribute("user")).getName();
        activity.setEditBy(editBy);
        activity.setEditTime(editTime);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag=as.update(activity);
        PrintJson.printJsonFlag(response,flag);
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        Map<String,Object> map=as.getUserListAndActivity(id);
        PrintJson.printJsonObj(response,map);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String[] ids = request.getParameterValues("id");
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
         boolean flag=as.delete(ids);
        PrintJson.printJsonFlag(response,flag);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
      String name=request.getParameter("name");
        String owner=request.getParameter("owner");
        String startDate=request.getParameter("startDate");
        String endDate=request.getParameter("endDate");
        String pageNostr=request.getParameter("pageNo");
        String pageSizestr=request.getParameter("pageSize");
        int pageNo=Integer.valueOf(pageNostr);
        int pageSize=Integer.valueOf(pageSizestr);
        int start=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("start",start);
        ActivityService as= (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        PagitionVO<Activity> vo=as.pageList(map);
        PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        Activity activity=new Activity();
        String id= UUIDUtil.getUUID();
      /*String owner=
      String name=
      String startDate=
      String endDate=
      String cost=
      String description=*/
      ;
      reflectutils.makeRequestToObject(request, activity);
      String createTime= DateTimeUtil.getSysTime();
      String createBy=((User)request.getSession().getAttribute("user")).getName();
      activity.setId(id);
      activity.setCreateTime(createTime);
      activity.setCreateBy(createBy);
      ActivityService as =(ActivityService) ServiceFactory.getService(new ActivityServiceImp());
      boolean flag=as.save(activity);
      PrintJson.printJsonFlag(response,flag);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
            UserService us = (UserService)ServiceFactory.getService(new UserServiceImp());
            List<User> userList=us.getUserList();

            PrintJson.printJsonObj(response,userList);

    }
}
