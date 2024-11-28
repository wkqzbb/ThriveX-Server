package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.model.Config;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.ConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Api(tags = "项目配置123")
@RestController
@RequestMapping("/config")
@Transactional
public class ConfigController {
    @Resource
    private ConfigService configService;

    @GetMapping("/{group}")
    @ApiOperation("获取项目配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result list(@PathVariable("group") String group) {
        return Result.success(configService.list(group));
    }

    @PatchMapping("/{group}")
    @ApiOperation("修改项目配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result edit(@PathVariable("group") String group, @RequestBody Map<String, String> config) {
        configService.edit(group, config);
        return Result.success();
    }
}