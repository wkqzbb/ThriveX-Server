package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.model.User;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.UserService;
import liuyuyang.net.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping
    @ApiOperation("新增用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody User user) {
        try {
            boolean res = userService.save(user);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        User data = userService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = userService.removeById(id);

        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = userService.removeByIds(ids);

        return res ? Result.success() : Result.error();
    }

    @PatchMapping
    @ApiOperation("编辑用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody User user) {
        try {
            boolean res = userService.updateById(user);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<User> get(@PathVariable Integer id) {
        User data = userService.getById(id);
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation("获取用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<User>> list() {
        List<User> data = userService.list();
        return Result.success(data);
    }

    @GetMapping("/{page}/{size}")
    @ApiOperation("分页查询用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result list(@PathVariable Integer page, @PathVariable Integer size) {
        Page<User> data = userService.list(page, size);

        Map<String, Object> result = Paging.filter(data);

        return Result.success(result);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result login(@RequestBody User user) {
        userService.login(user);
        return Result.success();
    }
}
