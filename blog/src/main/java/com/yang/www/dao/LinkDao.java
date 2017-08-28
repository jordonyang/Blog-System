package com.yang.www.dao;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import com.yang.www.po.Link;
@Repository
public interface LinkDao {

    int add(Link link);

    int delete(Integer linkId);

    int update(Link link);

    Long getTotal(Map<String,Object> map);

    List<Link> getLinks(Map<String,Object> map);

    List<Link> list(Map<String,Object> map);
}
