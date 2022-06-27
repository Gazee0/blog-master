package com.xiaobin.blog.web;

import com.xiaobin.blog.pojo.User;
import com.xiaobin.blog.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Resource
    private UserServiceImpl userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
        }

    @PostMapping(value = "/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes attributes){
         User user=userService.checkUser(username, password);
         if (user!=null){
             user.setPassword(null);
             session.setAttribute("newuser", user);
             session.setAttribute("user", user.getUsername());
             session.setAttribute("avatar", user.getAvatar());
             return "admin/index";
         }else {
             //给前端返回提示
             attributes.addFlashAttribute("message","用户名或密码错误");
             //使用重定向，这样路径才不会有问题
             return "redirect:/admin";
         }

    }

    @GetMapping("logout")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
