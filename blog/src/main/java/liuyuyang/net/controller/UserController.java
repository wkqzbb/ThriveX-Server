package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.dto.user.EditPassDTO;
import liuyuyang.net.dto.user.UserInfoDTO;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.model.Role;
import liuyuyang.net.model.User;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.RoleService;
import liuyuyang.net.service.UserService;
import liuyuyang.net.utils.JwtUtils;
import liuyuyang.net.utils.Paging;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {
    @Resource
    private JwtProperties jwtProperties;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    @PostMapping
    @ApiOperation("新增用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success("新增成功");
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
    public Result<String> edit(@RequestBody UserInfoDTO data) {
        User user = userService.getById(data.getId());
        BeanUtils.copyProperties(data, user);

        boolean res = userService.updateById(user);

        return res ? Result.success() : Result.error();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取用户")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<User> get(@PathVariable Integer id) {
        User data = userService.get(id);
        return Result.success(data);
    }

    @GetMapping("/all")
    @ApiOperation("获取用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<User>> list() {
        List<User> list = userService.list();

        for (User user : list) {
            user.setPassword("只有聪明的人才能看到密码");
        }

        return Result.success(list);
    }

    @GetMapping
    @ApiOperation("分页查询用户列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        Page<User> data = userService.paging(page, size);
        Map<String, Object> result = Paging.filter(data);
        return Result.success(result);
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result login(@RequestBody User user) {
        User data = userService.login(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", data);
        String token = JwtUtils.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        Role role = roleService.getById(data.getRoleId());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", data);
        result.put("role", role);

        return Result.success("登录成功", result);
    }

    @PatchMapping("/pass")
    @ApiOperation("修改用户密码")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result<String> editPass(@RequestBody EditPassDTO data) {
        userService.editPass(data);
        return Result.success("密码修改成功");
    }
}
