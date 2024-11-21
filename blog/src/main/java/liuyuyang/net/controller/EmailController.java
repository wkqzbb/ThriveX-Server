package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.dto.email.DismissEmailDTO;
import liuyuyang.net.result.Result;
import liuyuyang.net.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/email")
@Transactional
public class EmailController {
    @Resource
    private EmailUtils emailUtils;
    @Resource
    private TemplateEngine templateEngine;

    @CheckRole
    @PostMapping("/dismiss")
    @NoTokenRequired
    @ApiOperation("驳回通知邮件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result dismiss(@RequestBody DismissEmailDTO email) throws Exception {
        // 处理邮件模板
        Context context = new Context();
        context.setVariable("type", email.getType());
        context.setVariable("recipient", email.getRecipient());
        context.setVariable("time", email.getTime());
        context.setVariable("content", email.getContent());
        context.setVariable("url", email.getUrl());
        String template = templateEngine.process("dismiss_email", context);

        emailUtils.send(email.getTo() != null ? email.getTo() : null, email.getSubject(), template);

        return Result.success();
    }
}