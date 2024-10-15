package liuyuyang.net.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailUtils {
    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void send(String to, String subject, String template) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // 发送邮件
        helper.setFrom(new InternetAddress(from, "你有新的消息~")); // 发送者
        helper.setTo(to != null && !to.isEmpty() ? to : from); // 接收者
        helper.setSubject(subject); // 邮件标题
        helper.setText(template, true); // 第二个参数为 true 表示发送 HTML 格式
        mailSender.send(message);
    }
}
