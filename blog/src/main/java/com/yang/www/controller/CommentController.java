package com.yang.www.controller;

import com.yang.www.po.Blog;
import com.yang.www.service.BlogService;
import com.yang.www.service.CommentService;
import com.yang.www.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yang.www.po.Comment;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 保存评论
     * @param comment
     * @param imageCode
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("/save")
    public void save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
                       HttpServletResponse response, HttpSession session) {
        commentService.save(comment, imageCode, request, response, session);
    }
}
