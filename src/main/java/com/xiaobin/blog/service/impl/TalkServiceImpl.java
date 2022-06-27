package com.xiaobin.blog.service.impl;

import com.xiaobin.blog.mapper.TalkMapper;
import com.xiaobin.blog.pojo.Talk;
import com.xiaobin.blog.service.TalkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TalkServiceImpl implements TalkService {
    @Resource
    private TalkMapper talkMapper;

    @Override
    public List<Talk> listAllTalk() {
        return talkMapper.listAllTalk();
    }

    @Override
    public Talk getTalk(Long id) {
        return talkMapper.getTalk(id);
    }

    @Override
    public Talk getTalkByContent(String content) {
        return talkMapper.getTalkByContent(content);
    }

    @Override
    public void saveTalk(Talk talk) {
        talk.setUpdateTime(new Date());
        talkMapper.saveTalk(talk);
    }

    @Override
    public void updateTalk(Talk talk) {
        talk.setUpdateTime(new Date());
        talkMapper.updateTalk(talk);
    }

    @Override
    public void deleteTalk(Long id) {
        talkMapper.deleteTalk(id);
    }
}
