package com.xiaobin.blog.service.impl;

import com.xiaobin.blog.mapper.TagMapper;
import com.xiaobin.blog.pojo.Tag;
import com.xiaobin.blog.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper mapper;

    @Transactional
    @Override
    public Integer saveTag(Tag tag) {
        return mapper.saveTag(tag);
    }

    @Override
    public Tag getTag(Long id) {
        return mapper.getTag(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return mapper.getTagByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return mapper.listTag();
    }

    @Override
    public List<Tag> listTagByString(String ids) {
        List<Tag> tags=new ArrayList<>();
        List<Long> longs =convertToList(ids);
        for (Long id:longs){
            tags.add(mapper.getTag(id));
        }
        return tags;
    }

    private List<Long> convertToList(String ids) {  //把前端的tagIds字符串转换为list集合
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return mapper.updateTag(tag);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        mapper.deleteTag(id);
    }

    @Override
    public List<Tag> getBlogTag() {
        return mapper.getBlogTag();
    }
}
