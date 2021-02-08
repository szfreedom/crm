package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {
    List<ClueActivityRelation> getListByclueId(String clueId);

    int delete(String id);
}
