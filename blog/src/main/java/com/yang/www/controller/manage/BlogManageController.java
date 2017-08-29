package com.yang.www.controller.manage;

import com.yang.www.po.Blog;
import com.yang.www.service.BlogService;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage/blog")
public class BlogManageController {

    private BlogService blogService;
    @Autowired
    public void setBlogService(BlogService blogService){
        this.blogService=blogService;
    }

    @RequestMapping("/list")
    public void list(@RequestParam(value="page",required=false)Integer page, @RequestParam(value="rows",required=false)Integer rows,
                     @RequestParam(value="title",required=false)String title, HttpServletResponse response){
        blogService.getBackendList(page,rows,title,response);
    }

    @RequestMapping("/save")
    public void save(Blog blog, HttpServletResponse response) {
        blogService.save(blog,response);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="ids",required=false)String ids,HttpServletResponse response) {
        System.out.println("ids"+ids);
        blogService.delete(ids,response);
    }

    /**
     * 展示要修改的博客的信息
     * @param blogId
     * @param response
     */
    @RequestMapping("/showSelectedBlog")
    public void showSelectedBlog(@RequestParam("id") Integer blogId,HttpServletResponse response){
        Blog blog=blogService.findById(blogId);
        JSONObject result= JSONObject.fromObject(blog);
        ResponseUtil.write(response, result);
    }
}
