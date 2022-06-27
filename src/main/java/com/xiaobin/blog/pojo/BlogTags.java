package com.xiaobin.blog.pojo;

/**
 * 该类定义了blog和tag的关系，是多对多的关系
 */
public class BlogTags {
    private Integer id;

    private Long tagId;

    private Long blogId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }
}