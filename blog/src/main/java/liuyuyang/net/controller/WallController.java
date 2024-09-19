package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.model.Wall;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.WallService;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "留言管理")
@RestController
@RequestMapping("/wall")
@Transactional
public class WallController {
    @Resource
    private WallService wallService;

    @NoTokenRequired
    @PostMapping("/{cate_id}")
    @ApiOperation("新增留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@PathVariable Integer cate_id, @RequestBody Wall wall) {
        boolean res = wallService.save(wall);
        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Wall data = wallService.getById(id);
        if (data == null) return Result.error("删除留言失败：该留言不存在");
        Boolean res = wallService.removeById(id);
        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = wallService.removeByIds(ids);
        return res ? Result.success() : Result.error();
    }

    @PatchMapping
    @ApiOperation("编辑留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Wall wall) {
        wallService.updateById(wall);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Wall> get(@PathVariable Integer id) {
        Wall data = wallService.get(id);
        return Result.success(data);
    }

    @NoTokenRequired
    @PostMapping("/list")
    @ApiOperation("获取留言列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Wall>> list(@RequestBody FilterVo filterVo, SortVO sortVo) {
        List<Wall> list = wallService.list(filterVo, sortVo);
        return Result.success(list);
    }

    @NoTokenRequired
    @PostMapping("/paging")
    @ApiOperation("分页查询留言列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestBody FilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        Page<Wall> list = wallService.paging(filterVo, sortVo, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @NoTokenRequired
    @PostMapping("/cate/{cate_id}")
    @ApiOperation("获取指定分类中所有留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result getWallList(@PathVariable Integer cate_id, PageVo pageVo) {
        Page<Wall> list = wallService.getWallList(cate_id, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @PatchMapping("/audit/{id}")
    @ApiOperation("审核指定留言")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result auditWall(@PathVariable Integer id) {
        Wall data = wallService.getById(id);
        if (data == null) {
            throw new CustomException(400, "该留言不存在");
        }

        // data.setAuditStatus(1);
        wallService.updateById(data);
        return Result.success();
    }
}
