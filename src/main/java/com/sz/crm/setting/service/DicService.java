package com.sz.crm.setting.service;

import com.sz.crm.setting.daomain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {
    Map<String, List<DicValue>> getAll();
}
