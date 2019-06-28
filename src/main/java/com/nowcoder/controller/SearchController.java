package com.nowcoder.controller;

import com.nowcoder.async.EventProducer;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Question;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @RequestMapping("/search")
    public String search(@RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset
            , @RequestParam(value = "count", defaultValue = "10") int count,Model model) {
        List<Question> questionList = null;
        try {
            questionList = searchService.searchQuestion(keyword, offset, count, "<em color="+"red"+">", "</em>");

            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q=questionService.getById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent()!=null) {
                q.setContent(question.getContent());
                }
                if (question.getTitle()!=null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", userService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos",vos);
            model.addAttribute("keyword",keyword);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }


        return "result";
    }
}
