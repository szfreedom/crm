package com.sz.crm.workbench.dao;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.workbench.daomain.Activity;
import com.sz.crm.workbench.daomain.Clue;
import com.sz.crm.workbench.daomain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueDao {
    List<User> getUserList();

    int save(Clue clue);

    int getTotalByCondition(Map<String, Object> map);

    List<Clue> getClueLiatByCondition(Map<String, Object> map);

    Clue detail(String id);

    int unbandActivity(String id);

    List<Activity> getActivityByClueIdAndByName(Map<String, Object> map);

    int band(ClueActivityRelation ca);

    Clue getById(String clueId);

    int delete(String clueId);
}
