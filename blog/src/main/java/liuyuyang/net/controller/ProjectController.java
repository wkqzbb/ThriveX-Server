package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.mapper.ConfigMapper;
import liuyuyang.net.model.Config;
import liuyuyang.net.result.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "项目管理")
@RestController
@RequestMapping("/project")
@Transactional
public class ProjectController {
    @Resource
    private ConfigMapper configMapper;

    @PatchMapping("/layout")
    @ApiOperation("修改布局")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result editLayout(@RequestBody Config config) {
        config.setId(1);
        System.out.println(config);
        configMapper.updateById(config);

        return Result.success();
    }

    @GetMapping("/layout")
    @ApiOperation("获取布局配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result getLayout() {
        Config data = configMapper.selectById(1);
        return Result.success(data);
    }
}
