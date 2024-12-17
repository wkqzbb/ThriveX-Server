package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.model.Route;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.RouteService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "路由管理")
@RestController
@RequestMapping("/route")
@Transactional
public class RouteController {
    @Resource
    private RouteService routeService;

    @CheckRole
    @PostMapping
    @ApiOperation("新增路由")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Route route) {
        try {
            boolean res = routeService.save(route);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new CustomException(400, e.getMessage());
        }
    }

    @CheckRole
    @DeleteMapping("/{id}")
    @ApiOperation("删除路由")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Route data = routeService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = routeService.removeById(id);

        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @DeleteMapping("/batch")
    @ApiOperation("批量删除路由")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = routeService.removeByIds(ids);

        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @PatchMapping
    @ApiOperation("编辑路由")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Route route) {
        try {
            boolean res = routeService.updateById(route);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new CustomException(400, e.getMessage());
        }
    }

    @CheckRole({"admin", "author", "user"})
    @GetMapping("/{id}")
    @ApiOperation("获取路由")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Route> get(@PathVariable Integer id) {
        Route data = routeService.getById(id);
        return Result.success(data);
    }

    @CheckRole
    @GetMapping
    @ApiOperation("获取路由列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Route>> list() {
        List<Route> data = routeService.list();
        return Result.success(data);
    }
}
