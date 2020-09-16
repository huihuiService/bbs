package com.graduation.bbs.controller;


import com.graduation.bbs.cache.RamCache;
import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.model.Blog;
import com.graduation.bbs.model.Comment;
import com.graduation.bbs.model.Question;
import com.graduation.bbs.service.AdminUserService;
import com.graduation.bbs.service.BlogService;
import com.graduation.bbs.service.CommentService;
import com.graduation.bbs.utils.FixUtils;
import com.graduation.bbs.utils.SessionUtils;
import com.graduation.bbs.vo.QuestionWriteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 博客controller
 *
 * @author
 *
 */
@Controller
public class BlogController {


    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;

    @Resource
    AdminUserService adminUserService;

    // 列表展示
    @GetMapping("/blog")
    public String blogList(Model model,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "limit", defaultValue = "10") Integer limit){
        Pagination<Blog> pageParam = blogService.selectAll(page, limit);
        // 结果
        model.addAttribute("blogList",pageParam.getData());
        model.addAttribute("pageParam",pageParam);

        return "blog/list";
    }

    @GetMapping("/blog/{page}/{limit}")
    public String blogListPage(
            @PathVariable int page,
            @PathVariable int limit,
            Model model){

        if (page < 1){
            page = 1;
        }
        Pagination<Blog> pageParam = blogService.selectAll(page, limit);
        // 结果
        model.addAttribute("blogList",pageParam.getData());
        model.addAttribute("pageParam",pageParam);

        return "blog/list";
    }

    // 写文章
    @GetMapping("/blog/write")
    public String toWrite(Model model, HttpServletRequest request) {
        SessionUtils.getUserInSession(request);
        AdminUser user = SessionUtils.getUserInSession(request);
        if (user == null){
            model.addAttribute("writeLoginMsg", "如需发布, 请先登录");
            return "login";
        }
        return "blog/write";
    }

    @PostMapping("/blog/write")
    public synchronized String write(QuestionWriteForm questionWriteForm){
        // 构建问题对象
        Blog blog = new Blog();

        blog.setBid(FixUtils.getUuid());
        blog.setTitle(questionWriteForm.getTitle());
        blog.setContent(questionWriteForm.getContent());
        blog.setSort(0);
        blog.setViews(0);
        blog.setLikes(0);

        blog.setAuthorId(questionWriteForm.getAuthorId());
        blog.setAuthorName(questionWriteForm.getAuthorName());

        blog.setGmtCreate(FixUtils.getTime());
        blog.setGmtUpdate(FixUtils.getTime());
        // 存储对象
        blogService.insert(blog);

        // 重定向到列表页面
        return "redirect:/blog";
    }

    // 阅读文章
    @GetMapping("/blog/read/{bid}")
    public String read(@PathVariable("bid") String bid, Model model){
        Blog blog = blogService.selectByBid(bid);
        blog.setViews(blog.getViews()+1);
        blogService.update(blog);
        model.addAttribute("blog",blog);

        // 查询评论.
        List<Comment> commentList = commentService.select(bid);
        model.addAttribute("commentList",commentList);

        return "blog/read";
    }

    // 点赞文章
    @PostMapping("/blog/like/{uid}/{bid}")
    @ResponseBody
    public Map<String, Object> like(@PathVariable("uid") Integer uid,
                                     @PathVariable("bid") String bid,
                                     Model model){
        Map<String, Object> responseMap = new HashMap<>();

        Blog blog = blogService.selectByBid(bid);

        Boolean isHaveLike = RamCache.getLikeAndPut(blog.getBid(), uid);
        if (isHaveLike) {
            responseMap.put("code", 500);
            responseMap.put("msg", "不能重复点赞");
            responseMap.put("data", null);
            return responseMap;
        }

        blog.setLikes(blog.getLikes()+1);
        blogService.update(blog);
        model.addAttribute("blog",blog);

        responseMap.put("code", 200);
        responseMap.put("msg", "");
        responseMap.put("data", null);

        // 查询评论.
        return responseMap;
    }


    // 编辑问题
    @GetMapping("/blog/editor/{uid}/{bid}")
    public synchronized String toEditor(@PathVariable("uid") Integer uid,
                                        @PathVariable("bid") String bid, Model model){

        Blog blog = blogService.selectByBid(bid);

        if (!blog.getAuthorId().equals(uid)){
            model.addAttribute("editError", "禁止非法编辑");
            return "redirect:/question";
        }

        model.addAttribute("blog",blog);

        return "blog/editor";
    }

    @PostMapping("/blog/editor")
    public String editor(Blog blog){
        Blog queryBlog = blogService.selectByBid(blog.getBid());

        queryBlog.setTitle(blog.getTitle());
        queryBlog.setContent(blog.getContent());
        queryBlog.setGmtUpdate(FixUtils.getTime());

        blogService.update(queryBlog);

        return "redirect:/blog/read/"+blog.getBid();
    }

    // 删除问题
    @GetMapping("/blog/delete/{uid}/{bid}")
    public String delete(Model model,
                         @PathVariable("uid") Integer uid,
                         @PathVariable("bid") String bid){
        AdminUser adminUser = adminUserService.selectByPrimaryKey(uid);
        if (adminUser == null || adminUser.getRoleLevel() != 0) {
            model.addAttribute("editError", "禁止非法删除");
            return "redirect:/blog";
        }

        Blog blog = blogService.selectByBid(bid);

        blogService.delete(blog.getId());
        // 重定向到列表页面
        return "redirect:/blog";
    }

    // 评论
    @PostMapping("/blog/comment/{bid}")
    public String comment(@PathVariable("bid") String bid, Comment comment){
        // 存储评论
        comment.setCommentId(FixUtils.getUuid());
        comment.setTopicCategory(1);
        comment.setGmtCreate(FixUtils.getTime());
        commentService.insert(comment);
        // 重定向到列
        return "redirect:/blog/read/"+bid;
    }

    @PostMapping("/blog/search")
    public String questionSearchListPage(
            @RequestParam(required = false) String searchVal,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit,
            Model model){

        if (page < 1){
            page = 1;
        }
        Pagination<Blog> pageParam = blogService.selectAll(searchVal, page, limit);
        // 结果
        model.addAttribute("blogList",pageParam.getData());
        model.addAttribute("pageParam",pageParam);

        model.addAttribute("searchVal", searchVal);

        return "blog/list";
    }




}

