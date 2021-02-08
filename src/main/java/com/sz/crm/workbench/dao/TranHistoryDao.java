package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.TranHistory;

public interface TranHistoryDao {
    int save(TranHistory tranHistory);

    void changeStage(TranHistory tranHistory);
}
