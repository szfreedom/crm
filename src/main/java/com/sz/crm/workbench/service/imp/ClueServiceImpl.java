package com.sz.crm.workbench.service.imp;

import com.sz.crm.setting.daomain.User;
import com.sz.crm.utils.DateTimeUtil;
import com.sz.crm.utils.UUIDUtil;
import com.sz.crm.vo.PagitionVO;
import com.sz.crm.workbench.dao.*;
import com.sz.crm.workbench.daomain.*;
import com.sz.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    //线索相关表
    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    //客户相关表
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;
    //联系人相关表
    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;
    //交易相关表
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;
    @Override
    public List<User> getUserList() {
        List<User> userList=clueDao.getUserList();
        return userList;
    }

    @Override
    public boolean save(Clue clue) {
        boolean flag=true;
        int count=clueDao.save(clue);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PagitionVO pageList(Map<String, Object> map) {
        int total=clueDao.getTotalByCondition(map);
       List<Clue> clueList=clueDao.getClueLiatByCondition(map);
        PagitionVO<Clue> vo=new PagitionVO<>();
        vo.setTotal(total);
       vo.setDateList(clueList);
        return vo;
    }

    @Override
    public Clue detail(String id) {
        Clue clue=clueDao.detail(id);
        return clue;
    }

    @Override
    public boolean unbandActivity(String id) {
        boolean flag=true;
        int count=clueDao.unbandActivity(id);
        if (count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public List<Activity> getActivityByClueIdAndByName(Map<String, Object> map) {
        List<Activity> activityList=clueDao.getActivityByClueIdAndByName(map);
        return activityList;
    }

    @Override
    public boolean band(String cid, String[] aid) {
        boolean flag=true;
        for (String id:aid){
            ClueActivityRelation ca=new ClueActivityRelation();
            ca.setId(UUIDUtil.getUUID());
            ca.setActivityId(id);
            ca.setClueId(cid);
            int count=clueDao.band(ca);
            if (count!=1){
                flag=false;
            }
        }
        return flag;
    }
    @Transactional
    public boolean convert(String clueId, Tran tran, String createBy,String a) {
        String createTime= DateTimeUtil.getSysTime();

        boolean flag=true;

        Clue clue=clueDao.getById(clueId);

        String company = clue.getCompany();

        Customer customer=customerDao.getCustomerByName(company);

        if (customer==null){
            customer=new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setName(company);
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            int count1=customerDao.save(customer);
            if (count1!=1){
                flag=false;
            }
        }

        Contacts contacts=new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(customer.getContactSummary());
        contacts.setAppellation(clue.getAppellation());;
        contacts.setAddress(clue.getAddress());

        int count2=contactsDao.save(contacts);
        if (count2!=1){
            flag=false;
        }
        List<ClueRemark> clueRemarkList=clueRemarkDao.getListByclueId(clueId);
        for (ClueRemark clueRemark:clueRemarkList){
            String noteContent = clueRemark.getNoteContent();
            CustomerRemark customerRemark=new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(noteContent);
            customerRemark.setEditFlag("0");
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setCustomerId(customer.getId());
            int count3=customerRemarkDao.save(customerRemark);
            if (count3!=1){
                flag=false;
            }

            ContactsRemark contactsRemark=new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setEditFlag("0");
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setContactsId(contacts.getId());
            int count4=contactsRemarkDao.save(contactsRemark);
            if (count4!=1){
                flag=false;
            }

            int count8=clueRemarkDao.delete(clueRemark.getId());
            if (count8!=1){
                flag=false;
            }

        }

        List<ClueActivityRelation> clueActivityRelationList=clueActivityRelationDao.getListByclueId(clueId);
        for (ClueActivityRelation clueActivityRelation:clueActivityRelationList){
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            int count5=contactsActivityRelationDao.save(contactsActivityRelation);
            if (count5!=1){
                flag=false;
            }
            int count9=clueActivityRelationDao.delete(clueActivityRelation.getId());
            if (count9!=1){
                flag=false;
            }
        }

        if ("a".equals(a)){
            /*
            id,money,name,expectedDate,stage,activityId,createBy,createTime
             */
            tran.setSource(clue.getSource());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setDescription(clue.getDescription());
            tran.setCustomerId(customer.getId());
            tran.setContactSummary(contacts.getContactSummary());
            tran.setContactsId(contacts.getId());

            int count6=tranDao.save(tran);
            if (count6!=1){
                flag=false;
            }
            TranHistory tranHistory=new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setStage(tran.getStage());
            tranHistory.setTranId(tran.getId());
            int count7=tranHistoryDao.save(tranHistory);
            if (count7!=1){
                flag=false;
            }

        }

        int count10=clueDao.delete(clueId);
        if (count10!=1){
            flag=false;
        }
        return flag;
    }


}
