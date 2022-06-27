package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    public Blog getBlog(Long id);
    public List<Blog> getAllBlog();
    public List<Blog> getAllIndexBlog();
    public List<Blog> getAllRecommendBlog(); //获取推荐博客
    List<Blog> searchAllBlog(Blog blog);  //后台根据标题、分类、推荐搜索博客
    List<Blog> getByTypeId(Long typeId);//通过Tyoe类型id查询博客
    List<Blog> getByTagId(Long tagId);//通过标签类型id查询博客
    public int saveBlog(Blog blog);
    public int updateBlog(Blog blog);
    public int deleteBlog(Long id);
    public List<Blog> searchIndexBlog(String query);//全局的博客搜索，根据内容或标题
    public Blog getDetailedBlog(Long id);
    public Map<String, List<Blog>> archiveBlog();
}
