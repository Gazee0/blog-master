package com.xiaobin.blog.mapper;

import com.xiaobin.blog.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper {
    public Integer saveTag(Tag tag);

    public Tag getTag(Long id);
    public Tag getTagByName(String name);

    public List<Tag> listTag();
    List<Tag> getBlogTag();  //首页展示博客标签

    public int updateTag(Tag tag) ;

    public void deleteTag(Long id);
}