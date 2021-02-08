package com.sz.crm.workbench.dao;

import com.sz.crm.workbench.daomain.Customer;

import java.util.List;

public interface CustomerDao {
    Customer getCustomerByName(String name);

    int save(Customer customer);

    List<String> getCustomerName(String name);
}
