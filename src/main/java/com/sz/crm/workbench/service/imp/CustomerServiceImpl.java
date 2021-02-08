package com.sz.crm.workbench.service.imp;

import com.sz.crm.workbench.dao.CustomerDao;
import com.sz.crm.workbench.daomain.Customer;
import com.sz.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Override
    public List<String> getCustomerName(String name) {
        List<String> customerList=customerDao.getCustomerName(name);
        return customerList;
    }
}
