package com.graduation.bbs.controller;


import com.alibaba.fastjson.JSONObject;
import com.graduation.bbs.cache.RamCache;
import com.graduation.bbs.common.Pagination;
import com.graduation.bbs.model.AdminUser;
import com.graduation.bbs.model.Blog;
import com.graduation.bbs.model.Comment;
import com.graduation.bbs.model.Question;
import com.graduation.bbs.service.AdminUserService;
import com.graduation.bbs.service.CommentService;
import com.graduation.bbs.service.QuestionService;
import com.graduation.bbs.utils.FixUtils;
import com.graduation.bbs.utils.SessionUtils;
import com.graduation.bbs.vo.QuestionWriteForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 *
 * @author
 */
@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;
    @Resource
    CommentService commentService;

    @Resource
    AdminUserService adminUserService;


    // 问题列表展示
    @GetMapping("/question")
    public String questionList(Model model,
                               @RequestParam(value = "currPage", defaultValue = "1") Integer currPage,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Pagination<Question> list = questionService.selectAll(currPage, pageSize);
        model.addAttribute("pageParam", list);
        model.addAttribute("questionList", list.getData());
        return "question/list";
    }

    @GetMapping("/question/{page}/{limit}")
    public String questionListPage(
            @PathVariable int page,
            @PathVariable int limit,
            Model model){

        if (page < 1) {
            page = 1;
        }
        Pagination<Question> pageParam = questionService.selectAll(page, limit);
        // 结果
        model.addAttribute("questionList",pageParam.getData());
        model.addAttribute("pageParam",pageParam);

        return "question/list";
    }

    // 发布问题
    @GetMapping("/question/write")
    public String toWrite(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Object object = session.getAttribute("USER_INFO");
        if (object == null) {
            model.addAttribute("writeLoginMsg", "如需发布, 请先登录");
            return "login";
        } else {
            AdminUser adminUser = (AdminUser) object;
            if (adminUser == null) {
                model.addAttribute("writeLoginMsg", "如需发布, 请先登录");
                return "login";
            }
        }

        return "question/write";
    }

    @PostMapping("/question/write")
    public synchronized String write(QuestionWriteForm questionWriteForm){
        // 构建问题对象
        Question question = new Question();

        question.setQid(FixUtils.getUuid());
        question.setTitle(questionWriteForm.getTitle());
        question.setContent(questionWriteForm.getContent());
        question.setStatus(0);
        question.setSort(0);
        question.setViews(0);

        question.setAuthorId(questionWriteForm.getAuthorId());
        question.setAuthorName(questionWriteForm.getAuthorName());

        question.setGmtCreate(FixUtils.getTime());
        question.setGmtUpdate(FixUtils.getTime());
        // 存储对象
        questionService.insert(question);

        // 重定向到列表页面
        return "redirect:/question";
    }


    // 阅读问题
    @GetMapping("/question/read/{qid}")
    public String read(@PathVariable("qid") String qid,
                       HttpServletRequest request, Model model){
//        AdminUser user = SessionUtils.getUserInSession(request);
//        if (user == null){
//            model.addAttribute("")
//        }
        Question question = questionService.selectByQid(qid);
        question.setViews(question.getViews()+1);
        questionService.update(question);
        model.addAttribute("question",question);

        // 查询评论.
        List<Comment> commentList = commentService.select(qid);
        model.addAttribute("commentList",commentList);
        return "question/read";
    }

    // 评论
    @PostMapping("/question/comment/{qid}")
    public String comment(@PathVariable("qid") String qid, Comment comment){
        // 存储评论
        comment.setCommentId(FixUtils.getUuid());
        comment.setTopicCategory(2);
        comment.setGmtCreate(FixUtils.getTime());

        if (comment.getUserId() == null) {
            comment.setUserName("游客");
        }

        commentService.insert(comment);
        // 状态改为已解决
        Question question = questionService.selectByQid(qid);
        question.setStatus(1);
        questionService.update(question);
        // 重定向到列表页面
        return "redirect:/question/read/"+qid;
    }

    // 编辑问题
    @GetMapping("/question/editor/{uid}/{qid}")
    public synchronized String toEditor(@PathVariable("uid") Integer uid,
                                        @PathVariable("qid") String qid,Model model){

        Question question = questionService.selectByQid(qid);
        if (!question.getAuthorId().equals(uid)){
            model.addAttribute("editError", "禁止非法编辑");
            return "redirect:/question";
        }

        model.addAttribute("question",question);

        return "question/editor";
    }

    @PostMapping("/question/editor")
    public String editor(Question question){
        Question queryQuestion = questionService.selectByQid(question.getQid());

        queryQuestion.setTitle(question.getTitle());
        queryQuestion.setContent(question.getContent());
        queryQuestion.setGmtUpdate(FixUtils.getTime());

        questionService.update(queryQuestion);

        return "redirect:/question/read/"+question.getQid();
    }

    // 删除问题
    @GetMapping("/question/delete/{uid}/{qid}")
    public String delete(@PathVariable("uid") Integer uid,
                         @PathVariable("qid") String qid, Model model){

        AdminUser adminUser = adminUserService.selectByPrimaryKey(uid);
        if (adminUser == null || adminUser.getRoleLevel() != 0) {
            model.addAttribute("editError", "禁止非法删除");
            return "redirect:/question";
        }

        Question question = questionService.selectByQid(qid);

        questionService.delete(question.getId());

        // 重定向到列表页面
        return "redirect:/question";
    }


    // md 文件上传
    @ApiOperation(value = "md文件上传问题")
    @RequestMapping("/question/write/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {

        JSONObject res = new JSONObject();

        //获得SpringBoot当前项目的路径：System.getProperty("user.dir")
        String path = System.getProperty("user.dir")+"/src/main/resources/static/upload/";

        //按照月份进行分类：
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"月";
        path = path+month;

        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }

        //上传文件地址
        System.out.println("上传文件保存地址："+realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "file-"+ UUID.randomUUID().toString().replaceAll("-", "");
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i + 1);

        String outFilename = filename + "."+suffix;
        System.out.println("文件名：" + outFilename);

        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        File fileNew = new File(realPath +"/"+ outFilename);
        if (!fileNew.getParentFile().exists()) {
            boolean result = fileNew.getParentFile().mkdirs();
            if (!result) {
                System.out.println("创建失败");
                res.put("success", 0);
                return res;
            }
        }

        file.transferTo(fileNew);

        //给editormd进行回调

        res.put("url","/upload/"+month+"/"+ outFilename);
        res.put("success", 1);
        res.put("message", "upload success!");
        System.out.println(res.toJSONString());

        return res;
    }

    @PostMapping("/question/search")
    public String questionSearchListPage(
            @RequestParam(required = false) String searchVal,
            @RequestParam(defaultValue = "1", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer limit,
            Model model){

        if (page < 1){
            page = 1;
        }
        Pagination<Question> pageParam = questionService.selectAll(searchVal, page, limit);
        // 结果
        model.addAttribute("questionList",pageParam.getData());
        model.addAttribute("pageParam",pageParam);

        model.addAttribute("searchVal", searchVal);

        return "question/list";
    }


}

