package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import liuyuyang.net.model.File;
import liuyuyang.net.properties.OssProperties;
import liuyuyang.net.service.FileService;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class FileServiceImpl implements FileService {
    @Resource
    private OssProperties ossProperties;

    private Auth auth;
    private Configuration cfg;
    private BucketManager bucketManager;
    private UploadManager uploadManager;
    private String bucket;
    private String url;

    // 等注入完成后再执行
    @PostConstruct
    public void init() throws QiniuException {
        this.auth = Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
        this.cfg = new Configuration(Region.region0());
        this.bucketManager = new BucketManager(auth, cfg);
        this.uploadManager = new UploadManager(cfg);

        // 存储桶名称
        this.bucket = ossProperties.getBucket();
        // 存储桶域名
        this.url = "http://" + bucketManager.domainList(bucket)[0] + "/";
    }

    @Override
    public String add(MultipartFile file, String dir) throws IOException {
        String token = auth.uploadToken(bucket);

        // 获取文件字节数组
        byte[] fileBytes = file.getBytes();
        // 原始文件名称
        String originName = file.getOriginalFilename();

        // 将文件名称处理成hash值
        String hashFilename = DigestUtils.md5DigestAsHex(originName.getBytes());

        // 生成文件的存储路径和名称
        String filePath = dir + "/" + hashFilename;

        uploadManager.put(fileBytes, filePath, token);

        return url + filePath;
    }

    @Override
    public void del(String filePath) throws QiniuException {
        bucketManager.delete(bucket, filePath);
    }

    @Override
    public void batchDel(String[] pathList) throws QiniuException {
        for (String name : pathList) {
            bucketManager.delete(bucket, name);
        }
    }

    @Override
    public File get(String filePath) throws QiniuException {
        BucketManager bucketManager = new BucketManager(auth, cfg);

        FileInfo file = bucketManager.stat(bucket, filePath);

        String suffix = "." + file.mimeType.split("/")[1]; // 文件后缀
        String name = filePath.split("/")[1]; // 去掉目录的文件名

        File data = new File();
        data.setName(name + suffix);
        data.setSize(file.fsize);
        data.setType(file.mimeType);
        data.setCreateTime(file.putTime);
        data.setUrl(url + filePath);

        return data;
    }

    @Override
    public List<File> list(String dir, SortVO sortVo) throws QiniuException {
        // 文件名前缀
        String prefix = "all".equals(dir) ? "" : dir + "/";

        List<File> list = new ArrayList<>();

        // 列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix);

        while (fileListIterator.hasNext()) {
            // 处理获取的file list结果
            FileInfo[] items = fileListIterator.next();

            for (FileInfo item : items) {
                if ("all".equals(dir) || item.key.startsWith(prefix)) {
                    // 当 dir 不为 "all" 时，进一步过滤掉不在指定目录中的文件
                    String relativePath = item.key.substring(prefix.length());
                    if ("all".equals(dir) || !relativePath.contains("/")) {
                        String suffix = "." + item.mimeType.split("/")[1]; // 文件后缀

                        File file = new File();
                        // 如果 dir 为 "all"，保留完整的 item.key，否则去掉 prefix
                        String name = "all".equals(dir) ? item.key : item.key.replaceFirst("^" + prefix, "");
                        file.setName(name + suffix);
                        file.setSize(item.fsize);
                        file.setType(item.mimeType);
                        file.setCreateTime(item.putTime);
                        file.setUrl(url + item.key);

                        list.add(file);
                    }
                }
            }
        }

        switch (sortVo.getSort()) {
            case "asc":
                list.sort((a1, a2) -> a1.getCreateTime().compareTo(a2.getCreateTime()));
                break;
            case "desc":
                list.sort((a1, a2) -> a2.getCreateTime().compareTo(a1.getCreateTime()));
                break;
        }

        return list;
    }

    @Override
    public Page<File> paging(String dir, SortVO sortVo, PageVo pageVo) throws QiniuException {
        List<File> list = list(dir, sortVo);

        // 分页处理
        int currentPage = pageVo.getPage();
        int pageSize = pageVo.getSize();
        int total = list.size();
        int fromIndex = Math.min((currentPage - 1) * pageSize, total);
        int toIndex = Math.min(currentPage * pageSize, total);

        List<File> pagedFiles = list.subList(fromIndex, toIndex);

        // 封装分页结果
        Page<File> page = new Page<>(currentPage, pageSize, total);
        page.setRecords(pagedFiles);

        return page;
    }

}