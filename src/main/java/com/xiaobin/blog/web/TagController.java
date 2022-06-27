package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.pojo.Tag;
import com.xiaobin.blog.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping("/tags")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Tag> tags = tagService.listTag();
        //得到分页结果对象
        PageInfo<Tag> pageInfo = new PageInfo<>(tags);
        System.out.println(pageInfo.getTotal());

        model.addAttribute("pageInfo", pageInfo);
        return "admin/tags";
    }
    @PostMapping("/tags")
    public String types(Tag tag, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if(tag==null){
            attributes.addFlashAttribute("message", "标签添加失败，您未输入");
            return "redirect:/admin";
        }
        else if (t==null){
            //说明还不存在该分类，可以添加
            tagService.saveTag(tag);
        }else {
            //存在该分类，返回信息给前端
            attributes.addFlashAttribute("message", "添加失败：已存在该标签");
            //return "admin/tags-input";
            return "redirect:/admin/tagsinput";
        }
        attributes.addFlashAttribute("message", "添加成功");
        return "redirect:/admin/tags";
    }

    @GetMapping("/tagsinput")
    public String input(){
        return "admin/tags-input";
    }


    @Transactional
    @GetMapping("/tagsdelete")
    public String delete(@RequestParam Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }

    @GetMapping("/tagsupdate")
    public String input(Long id,Model model){
        Tag tag = tagService.getTag(id);
        model.addAttribute("tag", tag);
        return "admin/tags-update";
    }

    @PostMapping("/tagsupdate/{id}")
    public String update(@PathVariable long id, Tag tag,RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if (t!=null){
            attributes.addFlashAttribute("errormessage", "修改失败：已存在该分类");
        }else{
            tagService.updateTag(tag);
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

}
