package com.yang.www.service;

import com.yang.www.po.Blogger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BloggerService {

    Blogger getBloggerByName(String name);

    Blogger find();

    void update(Blogger blogger, HttpServletResponse response);

    String login(Blogger blogger, HttpServletRequest request);

    void save(MultipartFile imageFile, Blogger blogger, HttpServletRequest request, HttpServletResponse response);
}
