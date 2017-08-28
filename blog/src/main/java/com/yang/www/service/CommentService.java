package com.yang.www.service;

import com.yang.www.po.Comment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface CommentService {

    void examine(String ids,Integer state,HttpServletResponse response);

    void list(Integer page,Integer rows,Integer state,HttpServletResponse response);

    void delete(String ids,HttpServletResponse response);

    List<Comment> getComments(Map<String,Object> map);

    int add(Comment comment);

    void save(Comment comment, String imageCode, HttpServletRequest request, HttpServletResponse response, HttpSession session);
}
