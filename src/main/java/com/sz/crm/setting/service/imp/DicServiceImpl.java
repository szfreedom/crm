package com.sz.crm.setting.service.imp;

import com.sz.crm.setting.dao.DicTypeDao;
import com.sz.crm.setting.dao.DicValueDao;
import com.sz.crm.setting.daomain.DicType;
import com.sz.crm.setting.daomain.DicValue;
import com.sz.crm.setting.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;
    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map=new HashMap<>();
        List<DicType> dtlist=dicTypeDao.getTypeList();
        for (DicType dt:dtlist){
            String code=dt.getCode();
            List<DicValue> dvlist=dicValueDao.getListByCode(code);
            map.put(code+"List",dvlist);
        }


        return map;
    }
}
