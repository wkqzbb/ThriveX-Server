package liuyuyang.net.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import liuyuyang.net.model.File;
import liuyuyang.net.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    String accessKey = "xpquCtc-v1t-M3wY3b8WVCVACS1viMpUNY6aZPzg";
    String secretKey = "1h-V_vCrOt2UIoHf4x_Rj4GxfjsW_IINRz0VyzFQ";
    String bucket = "thrive";
    Auth auth = Auth.create(accessKey, secretKey);
    Configuration cfg = new Configuration(Region.region0());
    BucketManager bucketManager = new BucketManager(auth, cfg);

    @Override
    public List<File> list() throws QiniuException {
        // 文件名前缀
        String prefix = "";

        // 列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix);
        System.out.println(fileListIterator);

        // 获取存储桶的域名
        String url = bucketManager.domainList(bucket)[0];

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