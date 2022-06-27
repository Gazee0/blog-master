package com.xiaobin.blog.web;

import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class ArchivesController {
    @Resource
    private BlogService blogService;

    @Resource
    private AboutConfig aboutConfig;

    @RequestMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archiveMap", blogService.archiveBlog());
        //查询共有多少篇博客
        model.addAttribute("blogCount", blogService.getAllBlog().size());
        model.addAttribute("about", aboutConfig);
        return "archives";
    }
}
