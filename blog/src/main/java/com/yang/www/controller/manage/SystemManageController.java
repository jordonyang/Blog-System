package com.yang.www.controller.manage;

import com.yang.www.po.Blog;
import com.yang.www.po.BlogType;
import com.yang.www.po.Blogger;
import com.yang.www.service.BlogService;
import com.yang.www.service.BlogTypeService;
import com.yang.www.service.BloggerService;
import com.yang.www.service.LinkService;
import com.yang.www.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.yang.www.po.Link;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import net.sf.json.JSONObject;
@Controller
@RequestMapping("/manage/system")
public class SystemManageController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogService blogService;

    /**
     * 刷新缓存
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/refreshSystem")
    public void refreshSystem(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ServletContext application= RequestContextUtils.getWebApplicationContext(request).getServletContext();

        Blogger blogger=bloggerService.find(); // 获取博主信息
        blogger.setPassword(null);
        application.setAttribute("blogger", blogger);

        List<Link> linkList=linkService.getLinks(null); // 查询所有的友情链接信息
        application.setAttribute("linkList", linkList);

        List<BlogType> blogTypeCountList=blogTypeService.getBlogTypes(); // 查询博客类别以及博客的数量
        application.setAttribute("blogTypeCountList", blogTypeCountList);

        List<Blog> blogCountList=blogService.groupByDate(); // 根据日期分组查询博客
        application.setAttribute("blogCountList", blogCountList);

        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
    }
}
