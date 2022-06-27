package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.pojo.Type;
import com.xiaobin.blog.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Resource
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Type> types = typeService.listType();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(types);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }
    @PostMapping("/types")
    public String types(Type type, RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if(type==null){
            attributes.addFlashAttribute("message", "添加失败，您未输入");
            return "redirect:/admin/input";
        }
        else if (t==null){
            //说明还不存在该分类，可以添加
            typeService.saveType(type);
        }else {
            //存在该分类，返回给前端
            attributes.addFlashAttribute("message", "已存在该分类");
            return "redirect:/admin/input";
        }
        attributes.addFlashAttribute("message", "添加成功");
        return "redirect:/admin/types";
    }

    @GetMapping("/input")
    public String input(){
        return "admin/types-input";
    }


    @Transactional
    @GetMapping("/delete")
    public String delete(@RequestParam Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }

    @GetMapping("/update")
    public String input(Long id,Model model){
        Type type = typeService.getType(id);
        model.addAttribute("type", type);
        return "admin/types-update";
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable long id, Type type,RedirectAttributes attributes){
        Type t = typeService.getTypeByName(type.getName());
        if (t!=null){
            System.out.println("已存在该分类");
            attributes.addFlashAttribute("errormessage", "已存在该分类");
        }else{
            typeService.updateType(type);
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

}
