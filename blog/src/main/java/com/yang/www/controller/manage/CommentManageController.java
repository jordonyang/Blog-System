package com.yang.www.controller.manage;

import com.yang.www.service.CommentService;
import com.yang.www.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manage/comment")
public class CommentManageController {
    @Resource
    private CommentService commentService;

    @RequestMapping("/list")
    public void list(@RequestParam(value="page",required=false)Integer page,@RequestParam(value="rows",required=false)Integer rows,@RequestParam(value="state",required=false)Integer state,HttpServletResponse response){
        commentService.list(page,rows,state,response);
    }

    @RequestMapping("/examine")
    public void examine(@RequestParam(value="ids",required=false)String ids,@RequestParam(value="state",required=false)Integer state,HttpServletResponse response){
        commentService.examine(ids,state,response);
    }

    @RequestMapping("/delete")
    public void delete(@RequestParam(value="ids",required=false)String ids, HttpServletResponse response){
        commentService.delete(ids,response);
    }
}
