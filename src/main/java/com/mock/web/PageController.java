package com.mock.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping(value = "test")
    public ModelAndView testPage(){
        return new ModelAndView("test",null);
    }
}