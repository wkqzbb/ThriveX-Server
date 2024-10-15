package liuyuyang.net.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    public void send(String to, String subject, String title, String reviewers, String time, String content, String url) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 处理邮件模板
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("reviewers", reviewers);
        context.setVariable("time", time);
        context.setVariable("content", content);
        context.setVariable("url", url);
        String template = templateEngine.process("email", context);

        // 发送邮件
        helper.setFrom(from); // 发送者
        helper.setTo(to); // 接收者
        helper.setSubject(subject); // 邮件标题
        helper.setText(template, true); // 第二个参数为 true 表示发送 HTML 格式
        mailSender.send(message);
    }
}
