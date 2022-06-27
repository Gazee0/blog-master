package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.Type;

import java.util.List;


public interface TypeService {
   public Integer saveType(Type type);
   public Type getType(Long id);
   public Type getTypeByName(String name);
   public List<Type> listType();
   List<Type> getBlogType(Integer size);  //根据传入的数量-首页右侧展示type对应的博客数量
   public int updateType(Type type);
   public void deleteType(Long id);
}
