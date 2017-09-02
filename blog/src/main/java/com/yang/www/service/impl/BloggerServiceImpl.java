package com.yang.www.service.impl;

import com.yang.www.dao.BloggerDao;
import com.yang.www.po.Blogger;
import com.yang.www.service.BloggerService;
import com.yang.www.utils.CryptographyUtil;
import com.yang.www.utils.DateUtil;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService{

    private BloggerDao bloggerDao;
    @Autowired
    public void setBloggerDao(BloggerDao bloggerDao){
        this.bloggerDao=bloggerDao;
    }

    @Override
    public Blogger getBloggerByName(String name) {
        return bloggerDao.getBloggerByName(name);
    }

    @Override
    public Blogger find() {
        return bloggerDao.find();
    }

    /**
     *  后台-保存个人信息修改
     * @param imageFile
     * @param blogger
     * @param request
     * @param response
     */
    @Override
    public void save(MultipartFile imageFile, Blogger blogger, HttpServletRequest request, HttpServletResponse response) {
        if (!imageFile.isEmpty()) {
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
            try {
                imageFile.transferTo(new File(filePath + "static/userImages/" + imageName));
            }catch (Exception e){
                e.printStackTrace();
            }
            blogger.setImagePath(imageName);
        }
        int affectedRows = bloggerDao.update(blogger);
        StringBuffer result = new StringBuffer();
        if (affectedRows> 0) {
            result.append("<script language='javascript'>alert('修改成功！');</script>");
        } else {
            result.append("<script language='javascript'>alert('修改失败！');</script>");
        }
        ResponseUtil.write(response, result);
    }

    /**
     * 后台-修改密吗
     * @param blogger
     * @param response
     */
    @Override
    public void update(Blogger blogger,HttpServletResponse response) {
        int affectedRows=bloggerDao.update(blogger);
        JSONObject result=new JSONObject();
        if(affectedRows>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
    }

    /**
     * 登录后台管理系统
     * @param blogger
     * @param request
     * @return
     */
    @Override
    public String login(Blogger blogger, HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getName(), CryptographyUtil.encode(blogger.getPassword(), "blog"));
        try {
            subject.login(token);
            return "redirect:/manage/main.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("blogger", blogger);
            request.setAttribute("errorInfo", "用户名或密码错误！");
            return "login";
        }
    }
}
