package com.xiaobin.blog.service.impl;

import com.xiaobin.blog.NotFoundException;
import com.xiaobin.blog.mapper.TypeMapper;
import com.xiaobin.blog.pojo.Type;
import com.xiaobin.blog.service.TypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Resource
    private TypeMapper mapper;

    @Transactional
    @Override
    public Integer saveType(Type type) {
        return mapper.saveType(type);
    }

    @Override
    public Type getType(Long id) {
        return mapper.getType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return mapper.getTypeByName(name);
    }

    @Override
    public List<Type> listType() {
        return mapper.listType();
    }

    @Override
    public List<Type> getBlogType(Integer size) {
        return mapper.getBlogType(size);
    }


    @Transactional
    @Override
    public int updateType(Type type) {
        Type t = mapper.getType(type.getId());
        if (t==null){
            throw new NotFoundException("不存在该类型");
        }
        return mapper.updateType(type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        mapper.deleteType(id);
    }
}
