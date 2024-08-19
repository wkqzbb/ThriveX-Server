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

    private Auth auth;
    private Configuration cfg;
    private BucketManager bucketManager;
    private String bucket;
    private String url;

    // 等注入完成后再执行
    @PostConstruct
    public void init() throws QiniuException {
        this.auth = Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        this.cfg = new Configuration(Region.region0());
        this.bucketManager = new BucketManager(auth, cfg);

        this.bucket = ossProperties.getBucket();
        this.url = "http://" + bucketManager.domainList(bucket)[0];
    }

    @Override
    public File get(String name) throws QiniuException {
        BucketManager bucketManager = new BucketManager(auth, cfg);

        FileInfo file = bucketManager.stat(bucket, name);

        File data = new File();
        data.setName(name);
        data.setSize(file.fsize);
        data.setType(file.mimeType);
        data.setCreateTime(file.putTime);
        data.setUrl(url + "/" + name);

        return data;
    }

    @Override
    public List<File> list() throws QiniuException {
        // 文件名前缀
        String prefix = "";

        // 列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix);

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