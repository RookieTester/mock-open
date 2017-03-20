package com.mock.web;

import com.mock.dto.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2017/1/14.
 */
@Controller
@RequestMapping(value = "/page")
public class PageController {
    @RequestMapping(value = "mock")
    public ModelAndView mockPage(){
        return new ModelAndView("mock",null);
    }

    @RequestMapping(value = "imgupload")
    public ModelAndView imgPage(){
        return new ModelAndView("imgupload",null);
    }

    @RequestMapping(value = "ABTest")
    public ModelAndView abtestPage(){
        return new ModelAndView("ABTest",null);
    }

}