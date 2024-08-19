package liuyuyang.net.controller;

import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.model.File;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.FileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
@Transactional
public class FileController {
    @Resource
    private FileService fileService;

    @GetMapping
    public Result<List<File>> list() {
        try {
            List<File> list = fileService.list();
            return Result.success("获取文件列表成功", list);
        } catch (QiniuException e) {
            throw new YuYangException(e.code(), "获取文件列表失败：" + e.getMessage());
        }
    }
}
