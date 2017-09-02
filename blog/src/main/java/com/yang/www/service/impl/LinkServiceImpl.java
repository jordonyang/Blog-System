package com.yang.www.service.impl;

import com.yang.www.dao.LinkDao;
import com.yang.www.po.Link;
import com.yang.www.po.PageBean;
import com.yang.www.service.LinkService;
import com.yang.www.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements LinkService {

    private LinkDao linkDao;

    @Autowired
    public void setLinkDao(LinkDao linkDao){
        this.linkDao=linkDao;
    }

    @Override
    public List<Link> getLinks(Map<String, Object> map) {
        return linkDao.getLinks(map);
    }

    /**
     * 后台-删除链接
     * @param ids
     * @param response
     */
    @Override
    public void delete(String ids, HttpServletResponse response) {
        String []idsStr=ids.split(",");
        JSONObject result=new JSONObject();
        for(int i=0;i<idsStr.length;i++){
            linkDao.delete(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        ResponseUtil.write(response, result);
    }

    /**
     * 后台-保存链接信息的修改
     * @param link
     * @param response
     */
    @Override
    public void save(Link link, HttpServletResponse response) {
        int affectedRows=0;
        if(link.getLinkId()==null){
            affectedRows=linkDao.add(link);
        }else{
            affectedRows=linkDao.update(link);
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
     * 后台-获取所有链接
     * @param page
     * @param rows
     * @param response
     */
    @Override
    public void list(Integer page, Integer rows, HttpServletResponse response) {
        PageBean pageBean=new PageBean(page,rows);
        Map<String,Object> map=new HashMap<>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Link> linkList=linkDao.list(map);
        Long total=linkDao.getTotal(map);
        JSONObject result=new JSONObject();
        JSONArray jsonArray= JSONArray.fromObject(linkList);
        result.put("rows", jsonArray);
        result.put("total", total);
        ResponseUtil.write(response, result);
    }
}
