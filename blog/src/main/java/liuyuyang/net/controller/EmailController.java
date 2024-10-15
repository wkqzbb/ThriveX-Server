package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.dto.email.CommentEmailDTO;
import liuyuyang.net.utils.EmailUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/email")
@Transactional
public class EmailController {
    @Resource
    private EmailUtils emailUtils;

    @Resource
    private TemplateEngine templateEngine;

    @PostMapping("/comment")
    @NoTokenRequired
    @ApiOperation("发送评论邮件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public String comment(@RequestBody CommentEmailDTO email) throws Exception {
        // 处理邮件模板
        Context context = new Context();
        context.setVariable("title", email.getTitle());
        context.setVariable("reviewers", email.getReviewers());
        context.setVariable("time", email.getTime());
        context.setVariable("content", email.getContent());
        context.setVariable("url", email.getUrl());
        String template = templateEngine.process("comment_email", context);

        // emailUtils.send("liuyuyang1024@yeah.net", "测试标题", "好久不见，我回来了！", "宇阳", "2024年10月15日 14:44", "希望明年能够实现月薪过万的目标，这也是我的目标 哈哈哈", "https://liuyuyang.net");
        System.out.println(email);
        emailUtils.send(email.getTo() != null && !email.getTo().isEmpty() ? email.getTo() : null, email.getSubject(), template);

        return "发送邮件";
    }
}