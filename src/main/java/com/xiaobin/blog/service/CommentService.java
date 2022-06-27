package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.Comment;

import java.util.List;

public interface CommentService {
    //查询评论列表
    List<Comment> listCommentByBlogId(Long blogId);
    //保存评论
    int saveComment(Comment comment);


    void deleteComment(Comment comment, Long id);
}
