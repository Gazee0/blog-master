package com.xiaobin.blog.mapper;

import com.xiaobin.blog.pojo.Type;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TypeMapper {
    public Integer saveType(Type type);
    public Type getType(Long id);
    public Type getTypeByName(String name);
    public List<Type> listType();
    public List<Type> getBlogType(Integer size);
    public int updateType(Type type) ;
    public void deleteType(Long id);
}