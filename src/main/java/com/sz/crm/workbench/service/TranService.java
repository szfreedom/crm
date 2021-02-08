package com.sz.crm.workbench.service;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.workbench.daomain.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {


    boolean save(Tran tran, String customerName);

    Tran detail(String id);

    boolean changeStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getDataList();
}
