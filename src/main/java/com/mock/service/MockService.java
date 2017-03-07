package com.mock.service;

import com.mock.entity.MockInfo;
import com.mock.util.page.PageInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
public interface MockService {
    List<MockInfo> queryAll(PageInfo pageInfo);

    List<MockInfo> query();

    MockInfo queryById(int id);

    List<MockInfo> queryByAlias(PageInfo pageInfo,String alias);

    List<MockInfo> queryByProto(PageInfo pageInfo,String proto);

    List<MockInfo> queryByDomain(PageInfo pageInfo,String domain);

    List<MockInfo> queryByUrl(PageInfo pageInfo,String url);

    void insert(MockInfo mockInfo);

    void update(MockInfo mockInfo);

    void delete(int id);
}
