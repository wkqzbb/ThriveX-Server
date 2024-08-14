package liuyuyang.net.dto.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemDTO {
    @ApiModelProperty(value = "操作系统名称", example = "Windows 10")
    private String osName;

    @ApiModelProperty(value = "操作系统版本", example = "10.0.19042")
    private String osVersion;

    @ApiModelProperty(value = "总内存量 (MB)", example = "16384")
    private Integer totalMemory;

    @ApiModelProperty(value = "可用内存量 (MB)", example = "8192")
    private Integer availableMemory;

    @ApiModelProperty(value = "内存使用率 (%)", example = "50.0")
    private Float memoryUsage;
}
