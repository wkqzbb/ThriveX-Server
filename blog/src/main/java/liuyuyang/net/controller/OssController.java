package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.model.Oss;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.PageVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = "oss管理")
@RestController
@RequestMapping("/oss")
@AllArgsConstructor
public class OssController {
    private final OssService ossService;

    @CheckRole
    @PostMapping
    @ApiOperation("新增oss配置")
    @ApiOperationSupport(author = "laifeng", order = 1)
    public Result<String> add(@RequestBody Oss oss) {
        ossService.save(oss);
        return Result.success();
    }

    @CheckRole
    @PatchMapping
    @ApiOperation("更新oss配置")
    @ApiOperationSupport(author = "laifeng", order = 2)
    public Result<String> update(@RequestBody Oss oss) {
        if (oss.getIsEnable() == 1) return Result.error("删除oss配置失败：该配置正在使用中");
        ossService.updateById(oss);
        return Result.success();
    }

    @CheckRole
    @DeleteMapping("/{id}")
    @ApiOperation("删除oss配置")
    @ApiOperationSupport(author = "laifeng", order = 3)
    public Result<String> del(@PathVariable Integer id) {
        Oss oss = ossService.getById(id);
        if (oss == null) return Result.error("删除oss配置失败：该配置不存在");
        if (oss.getIsEnable() == 1) return Result.error("删除oss配置失败：该配置正在使用中");
        ossService.removeById(id);
        return Result.success();
    }

    @CheckRole
    @GetMapping("/{id}")
    @ApiOperation("获取oss配置")
    @ApiOperationSupport(author = "laifeng", order = 4)
    public Result<Oss> get(@PathVariable Integer id) {
        return Result.success(ossService.getById(id));
    }

    @CheckRole
    @PostMapping("/page")
    @ApiOperation("获取oss配置分页")
    @ApiOperationSupport(author = "laifeng", order = 5)
    public Result<Object> page(@RequestBody Oss oss, PageVo pageVo) {
        Page<Oss> list = ossService.ossPage(oss, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @CheckRole
    @PatchMapping("/enable/{id}")
    @ApiOperation("启用/禁用oss配置")
    @ApiOperationSupport(author = "laifeng", order = 6)
    public Result enable(@PathVariable Integer id) {
        ossService.enable(id);
        return Result.success();
    }


}
