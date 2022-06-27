package com.xiaobin.blog.service.impl;

import com.xiaobin.blog.NotFoundException;
import com.xiaobin.blog.mapper.BlogMapper;
import com.xiaobin.blog.mapper.BlogTagsMapper;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.BlogTags;
import com.xiaobin.blog.pojo.Tag;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.util.MarkdownUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    private BlogMapper mapper;

    @Resource
    private BlogTagsMapper blogTagsMappermapper;

    @Override
    public Blog getBlog(Long id) {
        return mapper.getBlog(id);
    }

    @Override
    public List<Blog> getAllBlog() {
        return mapper.listBlog();
    }

    @Override
    public List<Blog> getAllIndexBlog() {
        return mapper.listIndexBlog();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return mapper.getAllRecommendBlog();
    }

    @Override
    public List<Blog> searchAllBlog(Blog blog) {
        return mapper.searchAllBlog(blog);
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return mapper.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return mapper.getByTagId(tagId);
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        //进行一些初始化
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        mapper.saveBlog(blog);//存入数据库后，mybatis会给主键赋值
        //获取blog自增后的id,存入数据库
        Long blogId = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogTags blogTags=new BlogTags();
        for (Tag tag : tags) {
            blogTags.setBlogId(blogId);
            blogTags.setTagId(tag.getId());
            //存入数据库
            mapper.saveBlogAndTag(blogTags);
        }
        return 1;
    }

    //更新博客内容，并且更新t_blog_tags表
    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        Blog b = mapper.getBlog(blog.getId());
        if (b==null){
            throw new NotFoundException("该博客不存在");
        }
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogTags blogTags = new BlogTags();
        //删除所有t_blogs_tag中该博客的所有信息
        Long id=blog.getId();
        blogTagsMappermapper.deleteByBlogId(id);
        for (Tag tag : tags) {
            blogTags.setBlogId(blog.getId());
            blogTags.setTagId(tag.getId());
            //存入数据库
            mapper.saveBlogAndTag(blogTags);
        }
        blog.setUpdateTime(new Date());
        return mapper.updateBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return mapper.deleteBlog(id);
    }

    @Override
    public List<Blog> searchIndexBlog(String query) {
        return mapper.searchIndexBlog(query);
    }

    /**
     * 博客详情页面
     * @param id
     * @return
     */
    @Override
    public Blog getDetailedBlog(Long id) {
        Blog blog = mapper.getDetailedBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = mapper.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : set) {
            map.put(year, mapper.findByYear(year));
        }
        return map;
    }
}
