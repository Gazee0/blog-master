package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.Type;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TypeShowController {
    @Resource
    private TypeService typeService;

    @Resource
    private BlogService blogService;

    @Resource
    private AboutConfig aboutConfig;

    @GetMapping("/types/{tid}")
    public String typeShow(@PathVariable Long tid,
                           @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                           Model model){
        List<Type> blogTypes = typeService.getBlogType(1000);
        model.addAttribute("blogTypes", blogTypes);
        if(tid==-1){
            //从导航页点进来的，默认选择第一个标签
            //获得List数组的第一个元素
            tid=blogTypes.get(0).getId();
        }
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.getByTypeId(tid);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        ArrayList<Blog> newBlogs = new ArrayList<>();
        for (Blog blo:pageInfo.getList()) {
            Blog detailedBlog = blogService.getDetailedBlog(blo.getId());
            newBlogs.add(detailedBlog);
        }
        model.addAttribute("newBlogs",newBlogs);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("blogTypes",blogTypes);
        model.addAttribute("tid",tid);
        model.addAttribute("about", aboutConfig);
        return "/types";
    }

}
