package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import liuyuyang.net.model.File;
import liuyuyang.net.vo.PageVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    String add(MultipartFile file, String dir) throws IOException;

    void del(String filePath) throws QiniuException;

    void batchDel(String[] pathList) throws QiniuException;

    File get(String filePath) throws QiniuException;

    List<File> list(String dir) throws QiniuException;

    Page<File> paging(String dir, PageVo pageVo) throws QiniuException;
}
