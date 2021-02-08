package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.Tran;

import java.util.List;
import java.util.Map;

public interface TranDao {
    int save(Tran tran);


    Tran detail(String id);

    int changeStage(Tran tran);

    int getTotal();

    List<Map<String, Object>> getDataList();
}
