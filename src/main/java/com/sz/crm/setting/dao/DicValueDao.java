package com.sz.crm.setting.dao;

import com.sz.crm.setting.daomain.DicValue;

import java.util.List;

public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}
