package com.mock.service.impl;

import com.mock.dao.MockInfoDao;
import com.mock.entity.MockInfo;
import com.mock.service.MockService;
import com.mock.util.page.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
@Service
public class MockServiceImpl implements MockService {

    @Autowired
    private MockInfoDao mockInfoDao;

    @Override
    public List<MockInfo> queryAll(PageInfo pageInfo) {
        return this.mockInfoDao.queryAll(pageInfo);
    }

    @Override
    public MockInfo queryById(int id){
        return this.mockInfoDao.queryById(id);
    }

    @Override
    public List<MockInfo> query(){
        return this.mockInfoDao.query();
    }

    @Override
    public List<MockInfo> queryByAlias(PageInfo pageInfo,String alias) {
        return this.mockInfoDao.queryByAlias(pageInfo,alias);
    }

    @Override
    public List<MockInfo> queryByProto(PageInfo pageInfo,String proto) {
        return this.mockInfoDao.queryByProto(pageInfo,proto);
    }

    @Override
    public List<MockInfo> queryByDomain(PageInfo pageInfo,String domain) {
        return this.mockInfoDao.queryByDomain(pageInfo,domain);
    }

    @Override
    public List<MockInfo> queryByUrl(PageInfo pageInfo,String url) {
        return this.mockInfoDao.queryByUrl(pageInfo,url);
    }

    @Override
    public void insert(MockInfo mockInfo) {
        this.mockInfoDao.insert(mockInfo);
    }

    @Override
    public void update(MockInfo mockInfo){
        this.mockInfoDao.update(mockInfo);
    }

    @Override
    public void delete(int id){
        this.mockInfoDao.delete(id);
    }
}
