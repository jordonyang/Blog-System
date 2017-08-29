package com.yang.www.controller.manage;

import com.yang.www.service.LinkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.yang.www.po.Link;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
@Controller
@RequestMapping("/manage/link")
public class LinkManageController {

    @Resource
    private LinkService linkService;

    @RequestMapping("/list")
    public void list(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows,HttpServletResponse response){
        linkService.list(page,rows,response);
    }

    @RequestMapping("/save")
    public void save(Link link,HttpServletResponse response){
        linkService.save(link,response);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="ids",required=false)String ids, HttpServletResponse response){
        linkService.delete(ids,response);
    }
}
