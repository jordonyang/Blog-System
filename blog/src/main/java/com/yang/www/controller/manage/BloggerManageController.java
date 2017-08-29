package com.yang.www.controller.manage;

import com.yang.www.po.Blogger;
import com.yang.www.service.BloggerService;
import com.yang.www.utils.CryptographyUtil;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage/blogger")
public class BloggerManageController {

    private BloggerService bloggerService;
    @Autowired
    public void setBloggerService(BloggerService bloggerService){
        this.bloggerService=bloggerService;
    }

    /**
     * 获取站长信息
     * @param response
     */
    @RequestMapping("/find")
    public void find(HttpServletResponse response){
        Blogger blogger=bloggerService.find();
        JSONObject jsonObject= JSONObject.fromObject(blogger);
        ResponseUtil.write(response, jsonObject);
    }

    @RequestMapping("/save")
    public void save(@RequestParam(value="imageFile") MultipartFile imageFile, Blogger blogger, HttpServletRequest request, HttpServletResponse response){
        bloggerService.save(imageFile,blogger,request,response);
    }

    @RequestMapping("/modifyPassword")
    public void modifyPassword(String newPassword,HttpServletResponse response){
        Blogger blogger=new Blogger(CryptographyUtil.encode(newPassword,"blog"));
        bloggerService.update(blogger,response);
    }

    /**
     * 使用Shiro注销登录
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/login.jsp";
    }
}
