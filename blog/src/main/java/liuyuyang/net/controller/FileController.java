package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.model.File;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.FileService;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
@Transactional
public class FileController {
    @Resource
    private FileService fileService;

    @PostMapping
    public Result<String> add(@RequestParam("file") MultipartFile file, @RequestParam(defaultValue = "default") String dir) throws IOException {
        String url = fileService.add(file, dir);
        return Result.success("文件上传成功：" + url);
    }

    @GetMapping("/{name}")
    @ApiOperation("获取文件信息")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<File> get(@PathVariable String name) throws QiniuException {
        File data = fileService.get(name);
        return Result.success(data);
    }

    @GetMapping("/all")
    @ApiOperation("获取文件列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<List<File>> list(SortVO sortVo) throws QiniuException {
        List<File> list = fileService.list(sortVo);
        return Result.success(list);
    }

    @GetMapping
    @ApiOperation("分页查询文件列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result paging(SortVO sortVo, PageVo pageVo) throws QiniuException {
        Page<File> list = fileService.paging(sortVo, pageVo);
        Map<String, Object> result = Paging.filter((list));
        return Result.success(result);
    }
}
