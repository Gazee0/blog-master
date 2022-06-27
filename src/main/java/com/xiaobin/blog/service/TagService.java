package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.Tag;

import java.util.List;

public interface TagService {

    public Integer saveTag(Tag tag);
    public Tag getTag(Long id);
    public Tag getTagByName(String name);
    public List<Tag> listTag();

    public List<Tag> listTagByString(String ids); //从字符串获取tag集合
    public int updateTag(Tag tag);
    public void deleteTag(Long id);
    List<Tag> getBlogTag();  //首页展示博客标签
}
