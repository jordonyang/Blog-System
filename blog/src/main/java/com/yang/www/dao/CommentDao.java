package com.yang.www.dao;

import com.yang.www.po.Comment;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.List;
@Repository
public interface CommentDao {

    int add(Comment comment);

    int delete(Integer id);

    int update(Comment comment);

    Long getTotal(Map<String,Object> map);

    List<Comment> getComments(Map<String,Object> map);
}
