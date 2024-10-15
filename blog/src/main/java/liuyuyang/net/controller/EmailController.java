package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.utils.EmailUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@Api(tags = "邮件管理")
@RestController
@RequestMapping("/email")
@Transactional
public class EmailController {
    @Resource
    private EmailUtils emailUtils;

    @GetMapping
    @NoTokenRequired
    @ApiOperation("发送邮件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public String test() throws MessagingException {
        emailUtils.send("liuyuyang1024@yeah.net", "测试标题", "好久不见，我回来了！", "宇阳", "2024年10月15日 14:44", "希望明年能够实现月薪过万的目标，这也是我的目标 哈哈哈", "https://liuyuyang.net");
        return "发送邮件";
    }
}