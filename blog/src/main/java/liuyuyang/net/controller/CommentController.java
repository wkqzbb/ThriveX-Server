package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.model.Comment;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.CommentService;
import liuyuyang.net.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "评论管理")
@RestController
@RequestMapping("/comment")
@Transactional
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping
    @ApiOperation("新增评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Comment comment) {
        try {
            boolean res = commentService.save(comment);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        Comment data = commentService.getById(id);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = commentService.removeById(id);

        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        Boolean res = commentService.removeByIds(ids);

        return res ? Result.success() : Result.error();
    }

    @PatchMapping
    @ApiOperation("编辑评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Comment comment) {
        try {
            boolean res = commentService.updateById(comment);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new YuYangException(400, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("获取评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Comment> get(@PathVariable Integer id) {
        Comment data = commentService.get(id);
        return Result.success(data);
    }

    @GetMapping("/all")
    @ApiOperation("获取评论列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Comment>> list(@ApiParam(value = "默认为recursion模式，表示将子分类都递归到children中。如果设置了list模式，则直接返回所有评论") @RequestParam(defaultValue = "recursion") String pattern) {
        List<Comment> data = commentService.list(pattern);
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation("分页查询评论列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        Page<Comment> data = commentService.paging(page, size);

        Map<String, Object> result = Paging.filter(data);

        return Result.success(result);
    }

    @GetMapping("/article/{aid}")
    @ApiOperation("获取指定文章中所有评论")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result<List<Comment>> getCommentList(@PathVariable Integer aid) {
        List<Comment> data = commentService.getCommentList(aid);
        return Result.success(data);
    }
}
