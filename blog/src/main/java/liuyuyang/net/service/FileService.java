package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import liuyuyang.net.model.File;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    public String add(MultipartFile file, String dir) throws IOException;

    public void del(String name) throws QiniuException;

    public void batchDel(String[] names) throws QiniuException;

    public File get(String name) throws QiniuException;

    public List<File> list(SortVO sortVo) throws QiniuException;

    public Page<File> paging(SortVO sortVo, PageVo pageVo) throws QiniuException;
}
