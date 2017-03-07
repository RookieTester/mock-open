package com.mock.dao;

import com.mock.entity.MockInfo;
import com.mock.util.page.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */
public interface MockInfoDao {
    @Select(value = "select * from mock_info")
    @Results(value = { @Result(id = true, property = "id", column = "id"), @Result(property = "alias", column = "alias"),
            @Result(property = "proto", column = "proto"), @Result(property = "status", column = "status"),
            @Result(property = "domain", column = "domain"), @Result(property = "url", column = "url"),
            @Result(property = "bind", column = "bind"), @Result(property = "json", column = "json"),
            @Result(property = "port", column = "port"), @Result(property = "fileName", column = "filename") })
    List<MockInfo> queryAll(@Param("pageParam") PageInfo pageInfo);

    List<MockInfo> query();

    MockInfo queryById(int id);

    List<MockInfo> queryByAlias(@Param("pageParam") PageInfo pageInfo,@Param("alias")String alias);

    int[] queryAllPort();

    List<MockInfo> queryByProto(@Param("pageParam") PageInfo pageInfo,String proto);

    List<MockInfo> queryByDomain(@Param("pageParam") PageInfo pageInfo,String domain);

    List<MockInfo> queryByUrl(@Param("pageParam") PageInfo pageInfo,@Param("completeUrl") String completeUrl);

    void insert(MockInfo mockInfo);

    void update(MockInfo mockInfo);

    void delete(int id);
}
