package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.model.Cate;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.CateService;
import liuyuyang.net.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "分类管理")
@RestController
@RequestMapping("/cate")
@Transactional
public class CateController {
    @Resource
    private CateService cateService;

    @PostMapping
    @ApiOperation("新增分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Cate cate) {
        try {
            boolean res = cateService.save(cate);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        // 判断该分类中是否绑定了二级分类
        boolean e = cateService.exist(id);

        if (!e) return Result.error();

        Cate data = cateService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = cateService.removeById(id);

        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            boolean e = cateService.exist(id);

            if (!e) return Result.error();

            cateService.removeById(id);
        }

        return Result.success();
    }

    @PatchMapping
    @ApiOperation("编辑分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Cate cate) {
        try {
            boolean res = cateService.updateById(cate);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取分类")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Cate> get(@PathVariable Integer id) {
        Cate data = cateService.getById(id);
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation("获取分类列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Cate>> list() {
        List<Cate> data = cateService.list();
        return Result.success(data);
    }

    @GetMapping("/{page}/{size}")
    @ApiOperation("分页查询分类列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result list(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Cate> data = cateService.list(page, size);

        Map<String, Object> result = Paging.filter(data);

        return Result.success(result);
    }
}
