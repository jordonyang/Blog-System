package com.yang.www.dao;

import com.yang.www.po.BlogType;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;

@Repository
public interface BlogTypeDao {

    int add(BlogType blogType);

    int delete(Integer typeId);

    int update(BlogType blogType);

    Long getTotal(Map<String,Object>map);

    BlogType findById(Integer typeId);

    List<BlogType> getBlogTypes();

    List<BlogType> list(Map<String,Object>map);
}
