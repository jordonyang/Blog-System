package com.yang.www.controller;

import com.yang.www.lucene.BlogIndexer;
import com.yang.www.po.Blog;
import com.yang.www.service.BlogService;
import com.yang.www.service.BloggerService;
import com.yang.www.service.CommentService;
import com.yang.www.utils.PageUtil;
import com.yang.www.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("blog")
public class BlogController {

    private BlogService blogService;
    private CommentService commentService;
    private BlogIndexer blogIndexer=new BlogIndexer();

    @Autowired
    public void setBlogService(BlogService blogService){
        this.blogService=blogService;
    }
    @Autowired
    public void setCommentService(CommentService commentService){
        this.commentService=commentService;
    }

    /**
     * 通过lucene全文检索
     * @param input
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(value="input",required=false) String input, @RequestParam(value="page",required=false) Integer page, HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        int pageSize=10;
        if(page==null){
            page=1;
        }
        List<Blog> blogList= blogIndexer.searchBlog(input);
        Integer toIndex = blogList.size() >= page * pageSize ? page * pageSize : blogList.size();
        mav.addObject("pageTitle", "'"+input+"'-搜索结果");
        mav.addObject("mainPage", "foreground/blog/result.jsp");
        mav.addObject("blogList", blogList.subList((page-1)*pageSize, toIndex));
        mav.addObject("pageCode",PageUtil.genUpAndDownPageCode(page, blogList.size(), input, pageSize, request.getServletContext().getContextPath()));
        mav.addObject("input", input);
        mav.addObject("resultTotal", blogList.size());
        mav.setViewName("mainTemp");
        return mav;
    }

    /**
     * 查看博客内容
     * @param blogId
     * @param request
     * @return
     */
    @RequestMapping("/articles/{blogId}")
    public ModelAndView getContent(@PathVariable("blogId") Integer blogId, HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        Blog blog=blogService.findById(blogId);
        String keywords=blog.getKeyword();
        if(StringUtil.isNotEmpty(keywords)){
            String arr[]=keywords.split(" ");
            mav.addObject("keywords", StringUtil.filterWhite(Arrays.asList(arr)));
        }else{
            mav.addObject("keywords",null);
        }
        mav.addObject("blog",blog);
        blog.setClicks(blog.getClicks()+1);
        blogService.update(blog);
        Map<String,Object> map=new HashMap<>();
        map.put("blogId", blog.getBlogId());
        map.put("state", 1);
        mav.addObject("commentList", commentService.getComments(map));
        mav.addObject("pageCode", PageUtil.getUpAndDownPageCode(blogService.getLastBlog(blogId), blogService.getNextBlog(blogId), request.getServletContext().getContextPath()));
        mav.addObject("pageTitle", blog.getTitle()+"-临风博客");
        mav.addObject("mainPage", "foreground/blog/view.jsp");
        mav.setViewName("mainTemp");
        return mav;
    }
}
