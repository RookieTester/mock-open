package com.mock.web;

import com.mock.service.MockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/3/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-*.xml"})
public class MockControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private MockController mockController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();
    }

    @Test
    public void execute() throws Exception {

    }

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void queryAll() throws Exception {

    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void queryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/mock/queryById?id=1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proto").exists());
    }

    @Test
    public void queryByAlias() throws Exception {

    }

    @Test
    public void queryByProto() throws Exception {

    }

    @Test
    public void queryByDomain() throws Exception {

    }

    @Test
    public void queryByUrl() throws Exception {

    }

    @Test
    public void edit() throws Exception {

    }

}