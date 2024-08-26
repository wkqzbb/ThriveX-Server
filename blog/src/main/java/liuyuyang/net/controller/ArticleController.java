package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import liuyuyang.net.annotation.NoTokenRequired;
import liuyuyang.net.dto.article.ArticleDTO;
import liuyuyang.net.model.Article;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.ArticleService;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.article.ArticleFillterVo;
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
    public Result<String> add(@RequestBody ArticleDTO article) {
        articleService.add(article);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@PathVariable Integer id) {
        articleService.get(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody List<Integer> ids) {
        articleService.delBatch(ids);
        return Result.success();
    }

    @PatchMapping
    @ApiOperation("编辑文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<String> edit(@RequestBody ArticleDTO article) {
        articleService.edit(article);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<Article> get(@PathVariable Integer id) {
        Article data = articleService.get(id);

        return Result.success(data);
    }

    @PostMapping("/list")
    @ApiOperation("获取文章列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result<List<Article>> list(@RequestBody ArticleFillterVo filterVo, SortVO sortVo) {
        List<Article> data = articleService.list(filterVo, sortVo);
        return Result.success(data);
    }

    @NoTokenRequired
    @PostMapping("/paging")
    @ApiOperation("分页查询文章列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result paging(@RequestBody ArticleFillterVo filterVo, SortVO sortVo, PageVo pageVo) {
        Page<Article> list = articleService.paging(filterVo, sortVo, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @GetMapping("/cate/{id}")
    @ApiOperation("获取指定分类的文章")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 8)
    public Result getArticleList(@PathVariable Integer id, SortVO sortVo, PageVo pageVo) {
        Page<Article> list = articleService.getArticleList(id, sortVo, pageVo);
        Map<String, Object> result = Paging.filter(list);
        return Result.success(result);
    }

    @GetMapping("/random")
    @ApiOperation("随机获取文章数据")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 9)
    public Result<List<Article>> getRandomArticles(@ApiParam(value = "默认随机获取5篇文章，可以通过count指定数量") @RequestParam(defaultValue = "5") Integer count) {
        List<Article> data = articleService.getRandomArticles(count);
        return Result.success(data);
    }

    @GetMapping("/hot")
    @ApiOperation("获取推荐文章数据")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 10)
    public Result<List<Article>> getRecommendedArticles(@ApiParam(value = "默认浏览量最高的5篇文章，可以通过count指定数量") @RequestParam(defaultValue = "5") Integer count) {
        List<Article> data = articleService.getRecommendedArticles(count);
        return Result.success(data);
    }
}
