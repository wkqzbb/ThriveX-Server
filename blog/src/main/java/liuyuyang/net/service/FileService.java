package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import liuyuyang.net.model.File;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;

import java.util.List;

public interface FileService {
    public File get(String name) throws QiniuException;
    public List<File> list(SortVO sortVo) throws QiniuException;
    public Page<File> paging(SortVO sortVo, PageVo pageVo) throws QiniuException;
}
