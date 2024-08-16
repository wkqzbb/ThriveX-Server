package liuyuyang.net.dto.project;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemDTO {
    @TableField(exist = false)
    @ApiModelProperty(value = "操作系统名称", example = "Windows 10")
    private String osName;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作系统版本", example = "10.0.19042")
    private String osVersion;

    @TableField(exist = false)
    @ApiModelProperty(value = "总内存量 (MB)", example = "16384")
    private Integer totalMemory;

    @TableField(exist = false)
    @ApiModelProperty(value = "可用内存量 (MB)", example = "8192")
    private Integer availableMemory;

    @TableField(exist = false)
    @ApiModelProperty(value = "内存使用率 (%)", example = "50.0")
    private Float memoryUsage;
}
