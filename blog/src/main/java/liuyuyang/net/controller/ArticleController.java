package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.execption.GuiguException;
import liuyuyang.net.model.Article;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.ArticleService;
import liuyuyang.net.utils.Paging;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "文章管理")
@RestController
@RequestMapping("/article")
@Transactional
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    @ApiOperation("新增文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> add(@RequestBody Article article) {
        try {
            boolean res = articleService.save(article);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new GuiguException(400, e.getMessage());
        }
    }

    @DeleteMapping("/{cid}")
    @ApiOperation("删除文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer cid) {
        Article data = articleService.getById(cid);
        if (data == null) return Result.error("该数据不存在");

        Boolean res = articleService.removeById(cid);

        return res ? Result.success() : Result.error();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> cids) {
        Boolean res = articleService.removeByIds(cids);

        return res ? Result.success() : Result.error();
    }

    @PatchMapping
    @ApiOperation("编辑文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody Article article) {
        try {
            boolean res = articleService.updateById(article);

            return res ? Result.success() : Result.error();
        } catch (Exception e) {
            throw new GuiguException(400, e.getMessage());
        }
    }

    @GetMapping("/{cid}")
    @ApiOperation("获取文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Article> get(@PathVariable Integer cid) {
        Article data = articleService.getById(cid);
        return Result.success(data);
    }

    @GetMapping
    @ApiOperation("获取文章列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Article>> list() {
        List<Article> data = articleService.list();
        return Result.success(data);
    }

    @GetMapping("/{page}/{size}")
    @ApiOperation("分页查询文章列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result list(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Article> data = articleService.list(page, size);

        Map<String, Object> result = Paging.filter(data);

        return Result.success(result);
    }
}
