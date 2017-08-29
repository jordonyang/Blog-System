package com.yang.www.controller;
import com.yang.www.po.Blogger;
import com.yang.www.service.BloggerService;
import com.yang.www.utils.CryptographyUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

    private BloggerService bloggerService;

    @Autowired
    public void setBloggerService(BloggerService bloggerService){
        this.bloggerService=bloggerService;
    }

    @RequestMapping("/login")
    public String login(Blogger blogger, HttpServletRequest request) {
           return bloggerService.login(blogger,request);
    }

    /**
     * 关于站长
     * @return
     * @throws Exception
     */
    @RequestMapping("/aboutMe")
    public ModelAndView aboutBlogger(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("pageTitle", "站长信息-临风博客");
        mav.addObject("mainPage", "foreground/blogger/info.jsp");
        mav.setViewName("mainTemp");
        return mav;
    }
}
