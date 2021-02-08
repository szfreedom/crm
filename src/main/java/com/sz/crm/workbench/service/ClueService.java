package com.sz.crm.workbench.service;


import com.sz.crm.setting.daomain.User;
import com.sz.crm.vo.PagitionVO;
import com.sz.crm.workbench.daomain.Activity;
import com.sz.crm.workbench.daomain.Clue;
import com.sz.crm.workbench.daomain.Tran;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList();

    boolean save(Clue clue);

    PagitionVO pageList(Map<String, Object> map);

    Clue detail(String id);


    boolean unbandActivity(String id);

    List<Activity> getActivityByClueIdAndByName(Map<String, Object> map);

    boolean band(String cid, String[] aid);


    boolean convert(String clueId, Tran tran, String createBy,String a);
}
