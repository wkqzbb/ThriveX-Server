package liuyuyang.net.dto.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OtherDTO {
    @ApiModelProperty(value = "邮箱配置", example = "{}", required = true)
    private String email;
}
