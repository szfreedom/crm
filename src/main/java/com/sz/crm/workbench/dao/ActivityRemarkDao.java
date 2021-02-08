package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String activityId);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);

    String editRemark(String id);

    int updateRemark(ActivityRemark activityRemark);
}
