package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {
    List<ClueRemark> getListByclueId(String clueId);

    int delete(String id);
}
