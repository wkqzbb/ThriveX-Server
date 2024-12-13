package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("oss")
public class Oss {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "平台")
    private String platform;

    @ApiModelProperty(value = "access_key")
    private String accessKey;

    @ApiModelProperty(value = "secret_key")
    private String secretKey;

    @ApiModelProperty(value = "end_point")
    private String endPoint;

    @ApiModelProperty(value = "bucketName")
    private String bucketName;

    @ApiModelProperty(value = "domain")
    private String domain;

    @ApiModelProperty(value = "base_path")
    private String basePath;

    /**
     * 是否启用 0:禁用 1：启用
     */
    @ApiModelProperty(value = "是否启用 0:禁用 1：启用")
    private Integer isEnable;
}
