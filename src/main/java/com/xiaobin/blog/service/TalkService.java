package com.xiaobin.blog.service;

import com.xiaobin.blog.pojo.Talk;

import java.util.List;

public interface TalkService {
    /**
     * 查询所有说说
     * @return
     */
    public List<Talk> listAllTalk();

    /**
     * 通过id查询说说
     * @param id
     * @return
     */
    public  Talk getTalk(Long id);

    /**
     * 通过内容查询说说
     * @param content
     * @return
     */
    public Talk getTalkByContent(String content);

    /**
     * 增加说说
     * @param talk
     */
    public void saveTalk(Talk talk);

    /**
     * 更新说说
     * @param talk
     */
    public void updateTalk(Talk talk);

    /**
     * 删除说说，通过talk的id
     * @param id
     */
    public void deleteTalk(Long id);
}
