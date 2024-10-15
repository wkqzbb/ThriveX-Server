package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.NoTokenRequired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/email")
@Transactional
public class EmailController {
    @Resource
    private JavaMailSender mailSender;

    @GetMapping
    @NoTokenRequired
    @ApiOperation("发送邮件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public String test() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("3311118881@qq.com");
        message.setTo("liuyuyang1024@yeah.net");
        message.setSubject("测试标题");
        message.setText("测试内容");

        mailSender.send(message);
        return "发送邮件";
    }
}