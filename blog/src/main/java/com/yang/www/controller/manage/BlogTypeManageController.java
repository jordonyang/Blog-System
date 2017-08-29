package com.yang.www.controller.manage;

import com.yang.www.po.BlogType;
import com.yang.www.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage/blogType")
public class BlogTypeManageController {

    private BlogTypeService blogTypeService;
    @Autowired
    public void setBlogTypeService(BlogTypeService blogTypeService){
        this.blogTypeService=blogTypeService;
    }

    @RequestMapping("/save")
    public void save(BlogType blogType,HttpServletResponse response){
        blogTypeService.save(blogType,response);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="ids",required=false)String ids,HttpServletResponse response){
        blogTypeService.delete(ids,response);
    }

    @RequestMapping("/list")
    public void list(@RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows, HttpServletResponse response) {
        blogTypeService.list(page,rows,response);
    }
}
