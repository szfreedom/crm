package com.sz.crm.workbench.service.imp;

import com.sz.crm.setting.dao.UserDao;
import com.sz.crm.setting.daomain.User;
import com.sz.crm.setting.service.UserService;
import com.sz.crm.setting.service.imp.UserServiceImp;
import com.sz.crm.utils.SqlSessionUtil;
import com.sz.crm.vo.PagitionVO;
import com.sz.crm.workbench.dao.ActivityDao;
import com.sz.crm.workbench.dao.ActivityRemarkDao;
import com.sz.crm.workbench.daomain.Activity;
import com.sz.crm.workbench.daomain.ActivityRemark;
import com.sz.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ActivityServiceImp implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;
    @Autowired
    private UserDao userDao;
    @Override
    public boolean save(Activity a) {
            boolean flag=true;
            int count=activityDao.save(a);
            if (count!=1){
                 flag=false;
            }
        return flag;
    }

    @Override
    public PagitionVO<Activity> pageList(Map<String, Object> map) {
        int total=activityDao.getTotalByCondition(map);
        List<Activity> dateList=activityDao.getActivityListByCondition(map);
        PagitionVO<Activity> vo=new PagitionVO<>();
        vo.setDateList(dateList);
        vo.setTotal(total);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag=true;
        int count1=activityRemarkDao.getCountByAids(ids);
        int count2=activityRemarkDao.deleteByAids(ids);
        if (count1!=count2){
            flag=false;
        }
        int count3=activityDao.delete(ids);
        if (count3!=ids.length){
            flag=false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map=new HashMap<>();

        List<User> userList = userDao.getUserList();
        map.put("uList",userList);
        Activity activity=activityDao.getActivityById(id);
        map.put("a",activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag=true;
        int count=activityDao.update(activity);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity=activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {
        List<ActivityRemark> activityRemarkList=activityRemarkDao.getRemarkListByAid(activityId);
        return activityRemarkList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemarkDao.deleteRemark(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag=true;
            int count=activityRemarkDao.saveRemark(activityRemark);
            if (count!=1){
                flag=false;
            }
        return flag;
    }

    @Override
    public String editRemark(String id) {
        String noteContent=activityRemarkDao.editRemark(id);
        return noteContent;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag=true;
        int count =activityRemarkDao.updateRemark(activityRemark);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> activity=activityDao.getActivityListByClueId(clueId);
        return activity;
    }

    @Override
    public List<Activity> getActivityByName(String name) {
        List<Activity> activityList=activityDao.getActivityByName(name);
        return activityList;
    }
}
