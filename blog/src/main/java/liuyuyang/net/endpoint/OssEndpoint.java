package liuyuyang.net.endpoint;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.annotation.CheckRole;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.model.File;
import liuyuyang.net.result.Result;
import liuyuyang.net.utils.OssUtil;
import liuyuyang.net.utils.Paging;
import liuyuyang.net.vo.PageVo;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.get.ListFilesResult;
import org.dromara.x.file.storage.core.get.RemoteFileInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统一文件上传
 *
 * @author laifeng
 * @date 2024/12/14
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file/plus")
@Transactional
@CheckRole
public class OssEndpoint {
    @Resource
    private FileStorageService fileStorageService;


    @PostMapping
    @ApiOperation("文件上传")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<Object> add(@RequestParam MultipartFile[] files) throws IOException {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            FileInfo fileInfo = fileStorageService.of(file)
                    .setPlatform(OssUtil.getPlatform())
                    .upload();
            if (fileInfo == null) {
                throw new CustomException("上传文件失败");
            }
            urls.add(fileInfo.getUrl());
        }

        return Result.success("文件上传成功：", urls);
    }

    @DeleteMapping
    @ApiOperation("删除文件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result<String> del(@RequestParam String filePath) throws QiniuException {
        boolean delete = fileStorageService.delete(filePath);
        return Result.status(delete);
    }

    @DeleteMapping("/batch")
    @ApiOperation("批量删除文件")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result batchDel(@RequestBody String[] pathList) throws QiniuException {
        for (String url : pathList) {
            boolean delete = fileStorageService.delete(url);
            if (!delete) {
                throw new CustomException("删除文件失败");
            }
        }
        return Result.success();
    }

    @GetMapping("/info")
    @ApiOperation("获取文件信息")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result<FileInfo> get(@RequestParam String filePath) throws QiniuException {
        FileInfo fileInfo = fileStorageService.getFileInfoByUrl(filePath);
        return Result.success(fileInfo);
    }

    @PostMapping("/list")
    @ApiOperation("获取文件列表")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result<List<RemoteFileInfo>> list(@RequestParam(defaultValue = "all") String dir) throws QiniuException {
        ListFilesResult result = fileStorageService.listFiles()
                .setPlatform(OssUtil.getPlatform())
                .setPath(dir) // 指定目录
                .listFiles();
        List<RemoteFileInfo> fileList = result.getFileList();
        return Result.success(fileList);
    }

//    @PostMapping("/paging")
//    @ApiOperation("分页查询文件列表")
//    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 6)
//    public Result paging(@RequestParam(defaultValue = "all") String dir, PageVo pageVo) throws QiniuException {
//        Page<File> list = fileService.paging(dir, pageVo);
//        Map<String, Object> result = Paging.filter((list));
//        return Result.success(result);
//    }

//    @GetMapping("/dir")
//    @ApiOperation("获取目录列表")
//    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 7)
//    public Result<List<String>> dirList() {
//        List<String> list = ossProperties.getDirList();
//        return Result.success(list);
//    }
}
