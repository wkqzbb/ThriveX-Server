package liuyuyang.net;

import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.OssUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.get.ListFilesResult;
import org.dromara.x.file.storage.core.get.RemoteDirInfo;
import org.dromara.x.file.storage.core.get.RemoteFileInfo;
import org.dromara.x.file.storage.core.upload.UploadPretreatment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author laifeng
 * @version 1.0
 * @date 2024/12/11 9:47
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssTest {
    @Resource
    private OssService ossService;
    @Resource
    private FileStorageService fileStorageService;//

    /**
     * 测试上传
     */
    @Test
    public void testUpload() {
        FileInfo upload = fileStorageService.of(new File("E:\\aaa\\jiang_test.jpeg"))
                .setPlatform(OssUtils.getPlatform())
                .upload();
        System.out.println(upload);
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        // 文件url地址 ps:注意！！！只有文件在该平台上传存在了，才能删除！！
        boolean delete = fileStorageService
                .delete("laifeng157.s3.cn-east-1.qiniucs.com/test/675edcd2cbbba3ad4b709c5c.jpeg");
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFilename("Oss_test.txt");
//        fileInfo.setUrl("https://laifeng157.oss-cn-qingdao.aliyuncs.com/675b9c09cbbbbaa883e36a6d.txt");
//        boolean delete = fileStorageService.getFileStorage(OssUtil.getPlatform()).delete(fileInfo);
        System.out.println(delete);
    }


    /**
     * 测试删除 - 文件不在该平台上传也能删除
     */
    @Test
    public void testDeleteNoAuth() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setBasePath("upload/");
        fileInfo.setPath("month/");
        fileInfo.setFilename("Oss_test.txt");

        // 删除地址为 basePath + path + filename 可以直接这只fileName值删除
        boolean delete = fileStorageService.getFileStorage(OssUtils.getPlatform()).delete(fileInfo);
        System.out.println(delete);
    }

    /**
     * 测试文件列表
     */
    @Test
    public void testList() {
        UploadPretreatment uploadPretreatment = fileStorageService.of()
                .setPlatform(OssUtils.getPlatform());
        System.out.println("-----------");
        Map<String, String> metadata = uploadPretreatment.getMetadata();
        System.out.println(metadata);
        ListFilesResult result = fileStorageService.listFiles()
                .setPlatform(OssUtils.getPlatform())
                .setPath("upload/") // 指定目录
                .listFiles();
        // 获取目录列表
        List<RemoteDirInfo> dirList = result.getDirList();
        for (RemoteDirInfo dirInfo : dirList) {
            System.out.println(dirInfo);
        }
        // 获取文件列表
        List<RemoteFileInfo> fileList = result.getFileList();
        for (RemoteFileInfo fileInfo : fileList) {
            System.out.println(fileInfo);
        }
        System.out.println();
    }
}