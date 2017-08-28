package com.yang.www.service;

import com.yang.www.po.Blog;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BlogService {

    void delete(String blogIds,HttpServletResponse response);

    void save(Blog blog,HttpServletResponse response);

    Blog getLastBlog(Integer blogId);

    Blog getNextBlog(Integer blogId);

    Blog findById(Integer blogId);

    int update(Blog blog);

    Integer add(Blog blog);

    Long getTotal(Map<String,Object> map);

    List<Blog> groupByDate();

    void getBackendList(Integer page, Integer rows, String title, HttpServletResponse response);

    ModelAndView getList(Integer page,Integer typeId,String dateStr,HttpServletRequest request);
}
