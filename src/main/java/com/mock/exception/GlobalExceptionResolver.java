package com.mock.exception;

import com.alibaba.fastjson.JSON;
import com.mock.dto.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 错误信息统一处理
 * 对未处理的错误信息做一个统一处理
 * Created by Administrator on 2016/12/1.
 *
 * @author zdm
 */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        LOG.error("访问" + httpServletRequest.getRequestURI() + "时出现异常，异常信息：" + e.getMessage());
        //这里有2种选择
        //模式1：跳转到定制化的错误页面
//        ModelAndView error=new ModelAndView("error");
//        error.addObject("异常信息："+e.getMessage());
//        error.addObject("异常类"+e.getClass().getSimpleName().replace("/","'"));
//        return error;

        //模式2：返回json格式的错误信息
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            BaseResult<String> baseResult = new BaseResult(false, e.getMessage());
            writer.write(JSON.toJSONString(baseResult));
            writer.flush();
        } catch (Exception ex) {
            LOG.error("Exception:", ex);
        }
        return null;
    }
}
