package com.xiaobin.blog.web;

import com.xiaobin.blog.config.AboutConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class AboutController {

    @Resource
    private AboutConfig aboutConfig;

    @RequestMapping("/about")
    public String archives(Model model){
        model.addAttribute("about", aboutConfig);
        return "about";
    }
}
