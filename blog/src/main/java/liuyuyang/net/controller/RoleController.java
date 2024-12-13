package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.model.Role;
import liuyuyang.net.model.Route;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.RoleService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@Transactional
public class RoleController {
    @Resource
    private RoleService roleService;

    @CheckRole
    @PostMapping
    @ApiOperation("新增角色")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Role role) {
        boolean res = roleService.save(role);
        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Role data = roleService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = roleService.removeById(id);
        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @DeleteMapping("/batch")
    @ApiOperation("批量删除角色")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = roleService.removeByIds(ids);
        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @PatchMapping
    @ApiOperation("编辑角色")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Role role) {
        boolean res = roleService.updateById(role);
        return res ? Result.success() : Result.error();
    }

    @CheckRole
    @GetMapping("/{id}")
    @ApiOperation("获取角色")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Role> get(@PathVariable Integer id) {
        Role data = roleService.getById(id);
        return Result.success(data);
    }

    @CheckRole
    @GetMapping
    @ApiOperation("获取角色列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Role>> list() {
        List<Role> data = roleService.list();
        return Result.success(data);
    }

    @CheckRole
    @GetMapping("/route")
    @ApiOperation("获取指定角色的路由列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<List<Route>> getRouteList(Integer id) {
        List<Route> list = roleService.getRouteList(id);
        return Result.success(list);
    }

    @CheckRole
    @PatchMapping("/bindingRoute/{id}")
    @ApiOperation("分配角色权限")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<String> bindingRoute(@PathVariable Integer id, @RequestBody List<Integer> ids) {
        roleService.bindingRoute(id, ids);
        return Result.success();
    }
}
