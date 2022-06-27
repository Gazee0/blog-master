package com.xiaobin.blog.web;

import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.pojo.Talk;
import com.xiaobin.blog.pojo.User;
import com.xiaobin.blog.service.TalkService;
import com.xiaobin.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 管理员talk界面控制类
 */
@Controller
@RequestMapping("/admin")
public class TalkController {
    @Resource
    private AboutConfig aboutConfig;

    @Resource
    private TalkService talkService;
    @Resource
    private UserService userService;

    @RequestMapping("/talks")
    public String returnTalk(Model model){
        List<Talk> talks = talkService.listAllTalk();
        model.addAttribute("talks", talks);
        model.addAttribute("about", aboutConfig);
        return "admin/talks";
    }
    @GetMapping("/talksinput")
    public String input(){
        return "admin/talks-input";
    }

    @GetMapping("/talksupdate")
    public String input(Long id,Model model){
        Talk talk = talkService.getTalk(id);
        model.addAttribute("talk", talk);
        return "admin/talks-update";
    }

    @Transactional
    @RequestMapping("/talksdelete")
    public String deleteTalk(@RequestParam Long id, RedirectAttributes attributes,Model model){
        talkService.deleteTalk(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/talks";
    }

    @PostMapping("/talks")
    public String saveTalks(Talk talk, HttpSession session, RedirectAttributes attributes){
        System.out.println("--------------"+talk.getId());
       // Talk tal = talkService.getTalkByContent(talk.getContent());
        if (talk.getId()==null){
            //获取当前登录的管理员用户
            String username= (String) session.getAttribute("user");
            //查询该管理员的id
            User user = userService.findByUsername(username);
            talk.setUserId(Math.toIntExact(user.getId()));
            //说明还不存在该分类，可以添加
            talkService.saveTalk(talk);
            //存在该分类，返回给前端
            attributes.addFlashAttribute("message", "添加成功");

        }else { //更新

            talkService.updateTalk(talk);
            attributes.addFlashAttribute("message", "更新成功");
        }

        return "redirect:/admin/talks";
    }

}
