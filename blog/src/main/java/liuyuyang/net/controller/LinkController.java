package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.mapper.LinkTypeMapper;
import liuyuyang.net.model.Link;
import liuyuyang.net.model.LinkType;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.LinkService;
import liuyuyang.net.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "网站管理")
@RestController
@RequestMapping("/link")
@Transactional
public class LinkController {
    @Resource
    private LinkService linkService;
    @Resource
    private LinkTypeMapper linkTypeMapper;

    @PostMapping
    @ApiOperation("新增网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Link link) {
        try {
            boolean res = linkService.save(link);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Link data = linkService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = linkService.removeById(id);

        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = linkService.removeByIds(ids);

        return res ? Result.success() : Result.error();
    }

    @PatchMapping
    @ApiOperation("编辑网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Link link) {
        try {
            boolean res = linkService.updateById(link);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Link> get(@PathVariable Integer id) {
        Link data = linkService.getById(id);
        return Result.success(data);
    }

    @GetMapping("/all")
    @ApiOperation("获取网站列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Link>> list() {
        List<Link> data = linkService.list();
        return Result.success(data);
    }

    @GetMapping("/type")
    @ApiOperation("获取网站类型列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<List<LinkType>> typeList() {
        List<LinkType> data = linkTypeMapper.selectList(null);
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation("分页查询网站列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result paging(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        Page<Link> data = linkService.paging(page, size);

        Map<String, Object> result = Paging.filter(data);

        return Result.success(result);
    }
}
