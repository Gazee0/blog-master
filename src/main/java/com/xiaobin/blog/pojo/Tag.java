package com.xiaobin.blog.pojo;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private Long id;

    private String name;

    //多对多关系，一个Blog对应多个tag，一个tag对应多个blog
    private List<Blog> blogs=new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}