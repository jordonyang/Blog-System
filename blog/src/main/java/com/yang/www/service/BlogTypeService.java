package com.yang.www.service;

import com.yang.www.po.BlogType;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BlogTypeService {

    int add(BlogType blogType);

    void delete(String ids,HttpServletResponse response);

    int update(BlogType blogType);

    void save(BlogType blogType,HttpServletResponse response);

    List<BlogType> getBlogTypes();

    void list(Integer page,Integer rows,HttpServletResponse response);
}
