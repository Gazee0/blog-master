package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.Tag;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TagShowController {
    @Resource
    private TagService tagService;

    @Resource
    private BlogService blogService;

    @Resource
    private AboutConfig aboutConfig;

    @GetMapping("/tags/{id}")
    public String typeShow(@PathVariable Long id,
                           @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                           Model model){
        List<Tag> blogTags = tagService.getBlogTag();
        if(id==-1){
            //从导航页点进来的，默认选择第一个标签
            //获得List数组的第一个元素
            id=blogTags.get(0).getId();
        }
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.getByTagId(id);
        ArrayList<Blog> newBlogs = new ArrayList<>();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        //获得每一个blog的标签
        for (Blog blog:pageInfo.getList()) {
            Blog detailedBlog = blogService.getDetailedBlog(blog.getId());
            newBlogs.add(detailedBlog);
        }
        model.addAttribute("blogTags",blogTags);
        model.addAttribute("newBlogs",newBlogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("id",id);
        model.addAttribute("about", aboutConfig);
        return "/tags";
    }
}
