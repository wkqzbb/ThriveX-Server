package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.model.Rss;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.RssService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@Api(tags = "订阅管理")
@RestController
@RequestMapping("/rss")
public class RssController {
    @Resource
    private RssService rssService;

    @GetMapping
    @ApiOperation("获取订阅内容")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<List<Rss>> list() {
        List<Rss> list = rssService.list();

        return Result.success(list);
    }
}
