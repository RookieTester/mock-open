package com.mock.web;

import com.mock.entity.MockInfo;
import com.mock.service.MockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/1/14.
 */
@Controller
@RequestMapping(value = "/page")
public class PageController {
    @Autowired
    private MockService mockService;

    @RequestMapping(value = "mock")
    public ModelAndView mockPage(){
        return new ModelAndView("mock",null);
    }

    @RequestMapping(value = "modal-edit")
    public ModelAndView modalPage(@RequestParam(value = "id")Integer id){
        MockInfo mockInfo=mockService.queryById(id);
        ModelAndView mad=new ModelAndView("modal-edit");
        mad.addObject("json",mockInfo.getJson());
        mad.addObject("id",mockInfo.getId());
        mad.addObject("alias",mockInfo.getAlias());
        return mad;
    }
}