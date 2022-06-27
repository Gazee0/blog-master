package com.xiaobin.blog.mapper;

import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.BlogTags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    public Blog getBlog(Long id);
    //后端管理展示数据
    public List<Blog> listBlog();
    //首页展示博客数据
    public List<Blog> listIndexBlog();
    public int saveBlog(Blog blog);
    public int saveBlogAndTag(BlogTags blogTags);
    public int updateBlog(Blog blog);
    public int deleteBlog(Long id);
    List<Blog> getAllRecommendBlog();//获取推荐博客

    List<Blog> searchIndexBlog(@Param("query") String query);

    Blog getDetailedBlog(@Param(("id")) Long id);//文章详情

    List<Blog> searchAllBlog(Blog blog);//根据条件进行查询

    List<Blog> getByTypeId(@Param(("typeId")) Long typeId); //通过分类id查询博客

    List<Blog> getByTagId(@Param(("tagId")) Long tagId); //通过标签id查询博客

    List<String> findGroupYear();

    List<Blog> findByYear(String year);

}