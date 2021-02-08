package com.sz.crm.workbench.service.imp;

import com.sz.crm.utils.UUIDUtil;
import com.sz.crm.workbench.dao.CustomerDao;
import com.sz.crm.workbench.dao.TranDao;
import com.sz.crm.workbench.dao.TranHistoryDao;
import com.sz.crm.workbench.daomain.Customer;
import com.sz.crm.workbench.daomain.Tran;
import com.sz.crm.workbench.daomain.TranHistory;
import com.sz.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Autowired
    private CustomerDao customerDao;
    @Transactional
    @Override
    public boolean save(Tran tran, String customerName) {
        boolean flag=true;
        Customer customer=customerDao.getCustomerByName(customerName);
        if (customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(customerName);
            customer.setDescription(tran.getDescription());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            int count1 = customerDao.save(customer);
            if (count1!=1){
                flag=false;
            }

        }
        tran.setCustomerId(customer.getId());
        int count2=tranDao.save(tran);
        if (count2!=1){
            flag=false;
        }
        TranHistory tranHistory= new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        int count3 = tranHistoryDao.save(tranHistory);
        if (count3!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Tran detail(String id) {

        Tran tran=tranDao.detail(id);
        return tran;
    }

    @Override
    public boolean changeStage(Tran tran) {
        boolean flag=true;
        int count1=tranDao.changeStage(tran);

        if (count1!=1){
            flag=false;

        }
        TranHistory tranHistory=new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setStage(tran.getStage());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setCreateBy(tran.getEditBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());

        tranHistoryDao.changeStage(tranHistory);
        return flag;
    }

    @Override
    public int getTotal() {
        int total=tranDao.getTotal();
        return total;
    }

    @Override
    public List<Map<String, Object>> getDataList() {
        List<Map<String,Object>> dataList=tranDao.getDataList();
        return dataList;
    }
}

