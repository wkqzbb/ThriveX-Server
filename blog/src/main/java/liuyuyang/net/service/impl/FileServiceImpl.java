package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import liuyuyang.net.model.File;
import liuyuyang.net.properties.OssProperties;
import liuyuyang.net.service.FileService;
import liuyuyang.net.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        try {
            this.auth = Auth.create(ossProperties.getAccessKey(), ossProperties.getSecretKey());
            this.cfg = new Configuration(Region.region0());
            this.bucketManager = new BucketManager(auth, cfg);
            this.uploadManager = new UploadManager(cfg);

            // 存储桶名称
            this.bucket = ossProperties.getBucket();
            // 存储桶域名
            this.url = "https://" + bucketManager.domainList(bucket)[0] + "/";
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public String add(MultipartFile file, String dir) throws IOException {
        // 限制文件只能上传指定的目录当中
        List<String> dirList = ossProperties.getDirList();
        // 判断该目录是否在白名单内
        boolean isDir = dirList.contains(dir);
        if (!isDir) throw new RuntimeException("上传失败：该目录不在白名单内");

        dir = "root".equals(dir) ? "" : dir + "/";

        // 检查文件的 MIME 类型
        String contentType = file.getContentType();
        if (!isImage(contentType)) {
            throw new RuntimeException("上传失败：不支持的图片类型");
        }

        String token = auth.uploadToken(bucket);

        // 将文件内容转换为hash值
        String hashValue = DigestUtils.md5DigestAsHex(file.getBytes());

        // 获取文件的原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 构建新的文件名（hash值 + 后缀）
        String fileName = hashValue + suffix;

        // 上传文件
        uploadManager.put(file.getBytes(), dir + fileName, token);

        // 返回文件的URL
        return url + dir + fileName;
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
    public List<File> list(String dir) throws QiniuException {
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
                        File file = new File();
                        // 如果 dir 为 "all"，保留完整的 item.key，否则去掉 prefix
                        String name = "all".equals(dir) ? item.key : item.key.replaceFirst("^" + prefix, "");
                        file.setName(name);
                        file.setSize(item.fsize);
                        file.setType(item.mimeType);
                        file.setCreateTime(item.putTime);
                        file.setUrl(url + item.key);

                        list.add(file);
                    }
                }
            }
        }

       list.sort((a1, a2) -> a2.getCreateTime().compareTo(a1.getCreateTime()));

        return list;
    }

    @Override
    public Page<File> paging(String dir, PageVo pageVo) throws QiniuException {
        List<File> list = list(dir);

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

    // 判断上传的是不是图片格式
    private boolean isImage(String contentType) {
        if (contentType == null) {
            return false;
        }
        // 定义支持的图片 MIME 类型
        List<String> imageMimeTypes = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/bmp", "image/webp");
        return imageMimeTypes.contains(contentType);
    }
}