package liuyuyang.net.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import liuyuyang.net.model.File;
import liuyuyang.net.properties.OssProperties;
import liuyuyang.net.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Resource
    private OssProperties ossProperties;

    String bucket;
    Auth auth;
    Configuration cfg;
    BucketManager bucketManager;

    // 等注入完成后再执行
    @PostConstruct
    public void init() {
        this.bucket = ossProperties.getBucket();
        this.auth = Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        this.cfg = new Configuration(Region.region0());
        this.bucketManager = new BucketManager(auth, cfg);
    }

    @Override
    public List<File> list() throws QiniuException {
        // 文件名前缀
        String prefix = "";

        // 列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix);

        // 获取存储桶的域名
        String url = "http://" + bucketManager.domainList(bucket)[0];

        List<File> list = new ArrayList<>();

        while (fileListIterator.hasNext()) {
            // 处理获取的file list结果
            FileInfo[] items = fileListIterator.next();

            for (FileInfo item : items) {
                File file = new File();
                file.setName(item.key);
                file.setSize(item.fsize);
                file.setType(item.mimeType);
                file.setCreateTime(item.putTime);
                file.setUrl(url + "/" + item.key);

                list.add(file);
            }
        }

        return list;
    }
}