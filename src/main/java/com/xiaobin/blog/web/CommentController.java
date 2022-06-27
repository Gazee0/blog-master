package com.xiaobin.blog.web;

import com.xiaobin.blog.config.AboutConfig;
import com.xiaobin.blog.mapper.UserMapper;
import com.xiaobin.blog.pojo.Blog;
import com.xiaobin.blog.pojo.Comment;
import com.xiaobin.blog.pojo.User;
import com.xiaobin.blog.service.BlogService;
import com.xiaobin.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Resource
    private CommentService commentService;

    @Resource
    private BlogService blogService;

    @Resource
    private AboutConfig aboutConfig;

    @Resource
    private UserMapper userMapper;

    @Value("${comment.avatar}")
    private String avater;

    //查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        System.out.println("长度"+comments.size());
        System.out.println(comments.isEmpty());
        model.addAttribute("comments", comments);
        model.addAttribute("size", comments.size());
        return "blog :: commentList";
    }

    //新增评论
    @Transactional
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session,Model model) {
        Long blogId = comment.getBlogId();
        String userName = (String) session.getAttribute("user");
        //博主
        if (userName != null) {
            User user = userMapper.findByUsername(userName);
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {//非博主
            //设置头像
            comment.setAvatar(avater);
        }
        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    //    删除评论
    @Transactional
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment, Model model){
        commentService.deleteComment(comment,id);
        Blog blog = blogService.getDetailedBlog(blogId);
        model.addAttribute("blog", blog);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        model.addAttribute("about", aboutConfig);
        return "blog";
    }

}
