package com.yang.www.service.impl;

import com.yang.www.dao.BlogDao;
import com.yang.www.dao.BlogTypeDao;
import com.yang.www.po.BlogType;
import com.yang.www.po.PageBean;
import com.yang.www.service.BlogTypeService;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService{

    private BlogTypeDao blogTypeDao;
    private BlogDao blogDao;

    @Autowired
    public void setBlogDao( BlogDao blogDao){
        this.blogDao=blogDao;
    }
    @Autowired
    public void setBlogTypeDao(BlogTypeDao blogTypeDao){
        this.blogTypeDao=blogTypeDao;
    }

    @Override
    public List<BlogType> getBlogTypes() {
        return blogTypeDao.getBlogTypes();
    }

    @Override
    public int add(BlogType blogType) {
        return blogTypeDao.add(blogType);
    }

    @Override
    public int update(BlogType blogType) {
        return blogTypeDao.update(blogType);
    }

    /**
     * 后台管理系统-获取所有博客类型
     * @param page
     * @param rows
     * @param response
     */
    @Override
    public void list(Integer page,Integer rows,HttpServletResponse response) {
        PageBean pageBean=new PageBean(page,rows);
        Map<String,Object> map=new HashMap<>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BlogType> blogTypeList=blogTypeDao.list(map);
        Long total=blogTypeDao.getTotal(map);
        JSONObject result=new JSONObject();
        JSONArray jsonArray= JSONArray.fromObject(blogTypeList);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
    }

    /**
     * 删除博客类型
     * @param ids
     * @param response
     */
    @Override
    public void delete(String ids,HttpServletResponse response) {
        String []idsStr=ids.split(",");
        JSONObject result=new JSONObject();
        for(int i=0;i<idsStr.length;i++){
            if(blogDao.getBlogByTypeId(Integer.parseInt(idsStr[i]))>0){
                result.put("exist", "类别下有博客，不能删除！");
            }else{
                blogTypeDao.delete(Integer.parseInt(idsStr[i]));
            }
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
    }

    /**
     * 保存博客类型信息
     * @param blogType
     * @param response
     */
    @Override
    public void save(BlogType blogType,HttpServletResponse response) {
        int effectedRows=0;
        if(blogType.getTypeId()==null){
            effectedRows=blogTypeDao.add(blogType);
        }else{
            effectedRows=blogTypeDao.update(blogType);
        }
        JSONObject result=new JSONObject();
        if(effectedRows>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
    }
}
