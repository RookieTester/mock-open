package com.mock.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by Administrator on 2017/1/14.
 */
@Controller
@RequestMapping(value = "img")
public class ImgController {
    @RequestMapping(value = "upload")
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession,
                               @RequestParam(value="file",required=false )MultipartFile file)throws Exception{
        String filePath="";
        if (!file.isEmpty()) {
            try {
                // 文件保存路径
                filePath = request.getSession().getServletContext().getRealPath("/") + "resource/upload/"
                        + file.getOriginalFilename();
                // 转存文件
                file.transferTo(new File(filePath));
                System.out.println(filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("imgupload",null);
    }
}
