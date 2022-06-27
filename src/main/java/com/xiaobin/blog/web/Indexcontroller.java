package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.Tag;
import com.xiaobin.blog.pojo.Talk;
import com.xiaobin.blog.pojo.Type;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.service.TagService;
import com.xiaobin.blog.service.TalkService;
import com.xiaobin.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Indexcontroller {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;
    @Resource
    private AboutConfig aboutConfig;
    @Resource
    private TalkService talkService;

    private void getTypeAndTag(Model model){
        //可以写一个配置文件，用来配置标签显示个数和分类显示个数
        List<Type> blogTypes = typeService.getBlogType(6);
        List<Tag> blogTags = tagService.getBlogTag();
        model.addAttribute("blogTypes", blogTypes);
        model.addAttribute("blogTags", blogTags);
    }

    @RequestMapping(value = "/")
    public String index(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum,6);
        //获取分页博客列表
        List<Blog> allBlog = blogService.getAllIndexBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(allBlog);
        //获得每一个blog的标签
        ArrayList<Blog> newBlogs = new ArrayList<>();
        for (Blog blo:pageInfo.getList()) {
            Blog detailedBlog = blogService.getDetailedBlog(blo.getId());
            newBlogs.add(detailedBlog);
        }
        model.addAttribute("newBlogs",newBlogs);
        //获取分类与标签
        getTypeAndTag(model);
        //获取推荐博客
        List<Blog> recommendBlogs = blogService.getAllRecommendBlog();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("recommendBlogs", recommendBlogs);
        model.addAttribute("about", aboutConfig);
        return "index";
    }

    @RequestMapping(value = "/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        Blog blog = blogService.getDetailedBlog(id);
        model.addAttribute("blog", blog);
        model.addAttribute("about", aboutConfig);
        return "blog";
    }

    /**
     * 全局搜索
     * @param pagenum
     * @param query
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String globalSearch(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                               @RequestParam(required = false,defaultValue = "",value = "query" ) String query, Model model){
        PageHelper.startPage(pagenum, 6);
        List<Blog> searchBlog = blogService.searchIndexBlog(query);
        PageInfo<Blog> pageInfo = new PageInfo<>(searchBlog);
        ArrayList<Blog> newBlogs = new ArrayList<>();   
        for (Blog blo:pageInfo.getList()) {
            Blog detailedBlog = blogService.getDetailedBlog(blo.getId());
            newBlogs.add(detailedBlog);
        }
        model.addAttribute("newBlogs",newBlogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        model.addAttribute("about", aboutConfig);
        if (pageInfo.getTotal()==0){
            model.addAttribute("errorMessage", "未查到数据");
        }
        return "search";
    }

    @RequestMapping("/talk")
    public String returnTalk(Model model){
        List<Talk> talks = talkService.listAllTalk();
        model.addAttribute("talks", talks);
        model.addAttribute("about", aboutConfig);
        return "talk";
    }
}
