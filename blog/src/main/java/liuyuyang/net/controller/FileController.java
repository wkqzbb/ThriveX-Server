package liuyuyang.net.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.model.File;
import liuyuyang.net.properties.OssProperties;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.FileService;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.PageVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
@Transactional
@CheckRole
public class FileController {
    @Resource
    private FileService fileService;
    @Resource
    private OssProperties ossProperties;

    @PostMapping
    @ApiOperation("文件上传")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<Object> add(@RequestParam MultipartFile[] files, @RequestParam(defaultValue = "default") String dir) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.add(file, dir);
            urls.add(url);
        }

        return Result.success("文件上传成功：", urls);
    }

    @DeleteMapping
    @ApiOperation("删除文件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@RequestParam String filePath) throws QiniuException {
        fileService.del(filePath);
        return Result.success("文件删除成功");
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除文件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody String[] pathList) throws QiniuException {
        fileService.batchDel(pathList);
        return Result.success();
    }

    @GetMapping("/info")
    @ApiOperation("获取文件信息")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<File> get(@RequestParam String filePath) throws QiniuException {
        File data = fileService.get(filePath);
        return Result.success(data);
    }

    @PostMapping("/list")
    @ApiOperation("获取文件列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<List<File>> list(@RequestParam(defaultValue = "all") String dir) throws QiniuException {
        List<File> list = fileService.list(dir);
        return Result.success(list);
    }

    @PostMapping("/paging")
    @ApiOperation("分页查询文件列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
    public Result paging(@RequestParam(defaultValue = "all") String dir, PageVo pageVo) throws QiniuException {
        Page<File> list = fileService.paging(dir, pageVo);
        Map<String, Object> result = Paging.filter((list));
        return Result.success(result);
    }

    @GetMapping("/dir")
    @ApiOperation("获取目录列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
    public Result<List<String>> dirList() {
        List<String> list = ossProperties.getDirList();
        return Result.success(list);
    }
}
