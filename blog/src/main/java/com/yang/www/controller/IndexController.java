package com.yang.www.controller;

import com.yang.www.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController {

    private BlogService blogService;

    @Autowired
    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 前台-伪静态形式获取博客列表
     * @param page
     * @param typeId
     * @param releaseDateStr
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "typeId", required = false)
            Integer typeId, @RequestParam(value = "releaseDateStr",
            required = false) String releaseDateStr, HttpServletRequest request) {
        return blogService.getList(page, typeId, releaseDateStr, request);
    }
}