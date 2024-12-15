package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("file_part_detail")
public class FilePartDetail {

    @TableId
    private String id; // 分片id

    private String platform; // 存储平台

    private String uploadId; // 上传ID，仅在手动分片上传时使用

    private String eTag; // 分片 ETag

    private Integer partNumber; // 分片号。每一个上传的分片都有一个分片号，一般情况下取值范围是1~10000

    private Long partSize; // 文件大小，单位字节

    private String hashInfo; // 哈希信息

    private Date createTime; // 创建时间
}