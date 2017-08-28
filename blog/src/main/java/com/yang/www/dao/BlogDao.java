package com.yang.www.dao;

import com.yang.www.po.Blog;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
@Repository
public interface BlogDao {

    Integer add(Blog blog);

    int delete(Integer blogId);

    int update(Blog blog);

    Integer getBlogByTypeId(Integer typeId);

    Blog getLastBlog(Integer blogId);

    Blog getNextBlog(Integer blogId);

    Blog findById(Integer blogId);

    List<Blog> groupByDate();

    List<Blog> getList(Map<String,Object> map);

    Long getTotal(Map<String,Object> map);
}
