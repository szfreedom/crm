package com.sz.crm.setting.dao;

import com.sz.crm.setting.daomain.DicType;
import com.sz.crm.setting.daomain.DicValue;

import java.util.List;

public interface DicTypeDao {
    List<DicType> getTypeList();
}
