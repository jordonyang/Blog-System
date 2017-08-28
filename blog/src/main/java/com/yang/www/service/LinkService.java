package com.yang.www.service;

import com.yang.www.po.Link;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface LinkService {
    List<Link> getLinks(Map<String,Object> map);

    void delete(String ids, HttpServletResponse response);

    void save(Link link,HttpServletResponse response);

    void list(Integer page,Integer rows,HttpServletResponse response);
}
