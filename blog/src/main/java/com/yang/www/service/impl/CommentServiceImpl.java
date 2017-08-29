package com.yang.www.service.impl;

import com.yang.www.dao.BlogDao;
import com.yang.www.dao.CommentDao;
import com.yang.www.po.Blog;
import com.yang.www.po.Comment;
import com.yang.www.po.PageBean;
import com.yang.www.service.CommentService;
import com.yang.www.utils.DateJsonValueProcessor;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private BlogDao blogDao;

    @Autowired
    public void setCommentDao(CommentDao commentDao){
        this.commentDao=commentDao;
    }
    @Autowired
    public void setBlogDao(BlogDao blogDao){
        this.blogDao=blogDao;
    }
    @Override
    public int add(Comment comment) {
        return commentDao.add(comment);
    }
    @Override
    public List<Comment> getComments(Map<String, Object> map) {
        return commentDao.getComments(map);
    }

    @Override
    public void examine(String ids, Integer state, HttpServletResponse response) {
        String []idsStr=ids.split(",");
        for(int i=0;i<idsStr.length;i++){
            Comment comment=new Comment();
            comment.setCommentId(Integer.parseInt(idsStr[i]));
            comment.setState(state);
            commentDao.update(comment);
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
    }

    /**
     * 后台-获取所有评论
     * @param page
     * @param rows
     * @param state
     * @param response
     */
    @Override
    public void list(Integer page, Integer rows, Integer state, HttpServletResponse response) {
        PageBean pageBean=new PageBean(page,rows);
        Map<String,Object> map=new HashMap<>();
        map.put("state", state);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Comment> commentList=commentDao.getComments(map);
        Long total=commentDao.getTotal(map);
        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray= JSONArray.fromObject(commentList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
    }

    /**
     * 前台-用户评论提交
     * @param comment
     * @param imageCode
     * @param request
     * @param response
     * @param session
     */
    @Override
    public void save(Comment comment, String imageCode, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String sRand=(String) session.getAttribute("verificationCode");
        JSONObject result=new JSONObject();
        int effectRows=0;
        if(!imageCode.equals(sRand)){
            result.put("success", false);
            result.put("msg", "验证码填写错误!");
        }else{
            String userIp=request.getRemoteAddr();
            comment.setUserIp(userIp);
            if(comment.getCommentId()==null){
                effectRows=commentDao.add(comment);
                // 博客的回复次数加1
                Blog blog=blogDao.findById(comment.getBlog().getBlogId());
                blog.setCommentCount(blog.getCommentCount()+1);
                blogDao.update(blog);
            }
        }
        if(effectRows>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
    }

    /**
     * 后台-删除评论
     * @param ids
     * @param response
     */
    @Override
    public void delete(String ids,HttpServletResponse response) {
        String []idsStr=ids.split(",");
        JSONObject result=new JSONObject();
        for(int i=0;i<idsStr.length;i++){
            commentDao.delete(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
    }
}
