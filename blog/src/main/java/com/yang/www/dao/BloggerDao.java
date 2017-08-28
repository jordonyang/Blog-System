package com.yang.www.dao;

import com.yang.www.po.Blogger;
import org.springframework.stereotype.Repository;

@Repository
public interface BloggerDao {

     int update(Blogger blogger);

     Blogger getBloggerByName(String name);

     Blogger find();
}
