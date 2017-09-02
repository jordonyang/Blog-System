package com.yang.www.service.impl;

import com.yang.www.dao.BlogDao;
import com.yang.www.lucene.BlogIndexer;
import com.yang.www.po.Blog;
import com.yang.www.po.PageBean;
import com.yang.www.service.BlogService;
import com.yang.www.utils.DateJsonValueProcessor;
import com.yang.www.utils.PageUtil;
import com.yang.www.utils.ResponseUtil;
import com.yang.www.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    private static final int PAGE_SIZE=5;
    private BlogDao blogDao;
    private BlogIndexer blogIndexer=new BlogIndexer();

    @Override
    public Blog getLastBlog(Integer blogId) {
        return blogDao.getLastBlog(blogId);
    }

    @Override
    public Blog getNextBlog(Integer blogId) {
        return blogDao.getNextBlog(blogId);
    }

    @Override
    public Blog findById(Integer blogId) {
        return blogDao.findById(blogId);
    }

    @Override
    public Integer add(Blog blog) {
        return blogDao.add(blog);
    }

    @Override
    public int update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogDao.getTotal(map);
    }

    @Override
    public List<Blog> groupByDate() {
        return blogDao.groupByDate();
    }

    @Autowired
    public void setBlogDao(BlogDao blogDao){
        this.blogDao=blogDao;
    }

    /**
     * 删除博客，同时删除lucene生成的索引
     * @param blogIds
     * @param response
     */
    @Override
    public void delete(String blogIds,HttpServletResponse response) {
        String []idsStr=blogIds.split(",");
        for(int i=0;i<idsStr.length;i++){
            blogDao.delete(Integer.parseInt(idsStr[i]));
            //同时删除博客索引
            blogIndexer.deleteIndex(idsStr[i]);
        }
        JSONObject result=new JSONObject();
        result.put("success", true);
        ResponseUtil.write(response, result);
    }

    /**
     * 保存博客信息
     * @param blog
     * @param response
     */
    @Override
    public void save(Blog blog, HttpServletResponse response) {
        int affectedRows=0;
        if(blog.getBlogId()==null){
            affectedRows=blogDao.add(blog);
            blogIndexer.addIndex(blog);
        }else{
            affectedRows=blogDao.update(blog);
            blogIndexer.updateIndex(blog);
        }
        JSONObject result=new JSONObject();
        if(affectedRows>0){
            result.put("success", true);
        }else{
            result.put("success", false);
        }
        ResponseUtil.write(response, result);
    }

    /**
     * 获取后台博客管理中的博客列表
     * @param page
     * @param rows
     * @param title
     * @param response
     */
    @Override
    public void getBackendList(Integer page, Integer rows, String title, HttpServletResponse response) {
        PageBean pageBean=new PageBean(page,rows);
        Map<String,Object> map=new HashMap<>();
        map.put("title", title);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Blog> blogList=blogDao.getList(map);
        Long total=blogDao.getTotal(map);
        JSONObject result=new JSONObject();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray=JSONArray.fromObject(blogList, jsonConfig);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
    }

    /**
     * 分页展示的博客列表
     * @param page
     * @param typeId
     * @param dateStr
     * @param request
     * @return
     */
    @Override
    public ModelAndView getList(Integer page,Integer typeId,String dateStr,HttpServletRequest request) {
        ModelAndView mav=new ModelAndView();
        if(page==null){
            page=1;
        }
        PageBean pageBean=new PageBean(page,PAGE_SIZE);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId", typeId);
        map.put("releaseDateStr", dateStr);
        List<Blog> blogList=blogDao.getList(map);
        for(Blog blog:blogList){
            List<String> imageList=blog.getImageList();
            String blogContent=blog.getContent();
            Document doc= Jsoup.parse(blogContent);
            //抽取后缀为jpg的img标签
            Elements images=doc.select("img[src$=.jpg]");
            for(int i=0;i<images.size();i++){
                Element image=images.get(i);
                imageList.add(image.toString());
                if(i==2){
                    break;
                }
            }
        }
        mav.addObject("blogList", blogList);
        StringBuffer param=new StringBuffer();
        if(typeId!=null){
            param.append("typeId="+typeId+"&");
        }
        if(StringUtil.isNotEmpty(dateStr)){
            param.append("releaseDateStr="+dateStr+"&");
        }
        mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath()+"/index.html",
                blogDao.getTotal(map),page, PAGE_SIZE, param.toString()));
        mav.addObject("pageTitle", "临风博客");
        mav.addObject("mainPage", "foreground/blog/list.jsp");
        mav.setViewName("mainTemp");
        return mav;
    }
}
