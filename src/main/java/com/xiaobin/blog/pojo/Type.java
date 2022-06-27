package com.xiaobin.blog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private Long id;

    private String name;

    //一个Type里面对应多个blog
    private List<Blog> blogs=new ArrayList<>();

}