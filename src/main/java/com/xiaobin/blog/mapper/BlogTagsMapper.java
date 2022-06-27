package com.xiaobin.blog.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogTagsMapper {
    public int deleteByBlogId(Long blogId);
}
