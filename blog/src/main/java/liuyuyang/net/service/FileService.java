package liuyuyang.net.service;

import com.qiniu.common.QiniuException;
import liuyuyang.net.model.File;
import java.util.List;

public interface FileService {
    public File get(String name) throws QiniuException;
    public List<File> list() throws QiniuException;
}
