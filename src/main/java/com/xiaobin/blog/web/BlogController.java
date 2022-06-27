package com.xiaobin.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.User;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.service.TagService;
import com.xiaobin.blog.service.TypeService;
import com.xiaobin.blog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private TypeService typeService;
    @Resource
    private TagService tagService;
    @Resource
    private UserService userService;

    public void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
    }

    /**
     * 分页展示blog管理列表,分类检索功能未完成
     * @param blog
     * @param pagenum
     * @param model
     * @return
     */
    @RequestMapping("/blogs")
    public String blogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        List<Blog> blogs=null;
        PageHelper.startPage(pagenum,5);
        blogs = blogService.getAllBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);//查询类型和标签
        return "admin/blogs";
    }
    //    按照条件查询
    @RequestMapping(value = "/blogs/search",method = {RequestMethod.GET,RequestMethod.POST})
    public String searchBlogs(Blog blog, @RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum,5);
        List<Blog> blogs = blogService.searchAllBlog(blog);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }


    /**新增和更改公用一个方法
     * 保存文章
     * @param blog
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String save(Blog blog, HttpSession session, RedirectAttributes attributes){
        //获取当前登录的管理员用户
        String username= (String) session.getAttribute("user");
        //查询该管理员的id
        User user = userService.findByUsername(username);
        user.setPassword(null);
        //设置user
        blog.setUser(user);
        blog.setUserId(user.getId());//设置userid
        //blog.setTypeId(blog.getType().getId());//设置blog中typeId属性
        blog.setTags(tagService.listTagByString(blog.getTagIds()));//给blog中的List<Tag>赋值
        if (blog.getId()==null){//如果id为空，则为新增
            int b = blogService.saveBlog(blog);
        }else{
            blogService.updateBlog(blog);
            System.out.println("推荐："+blog.isRecommend()+" 转载消息："+blog.isShareStatement()+" 赞赏："+blog.isAppreciation()+" 评论："+blog.isCommentabled());
        //    同步更新tag_blog表
        }
        attributes.addFlashAttribute("message", "操作成功");
        //设置blog的type的id
        return "redirect:/admin/blogs";
    }




    /**
     * 返回发布博客页面
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);//查询类型和标签
        return "/admin/blogs-input";
    }
    @Transactional
    @GetMapping("/blogs/delete")
    public String delete(Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 跳转到修改博客内容页面
     * @param id
     * @param model
     * @return
     */
    @Transactional
    @GetMapping("/blogs/update")
    public String editInput(@RequestParam Long id,Model model){
        Blog blog = blogService.getBlog(id);
        System.out.println(blog.getFlag());
        setTypeAndTag(model);
        model.addAttribute("blog", blog);
        return "admin/blogs-update";
    }
}
