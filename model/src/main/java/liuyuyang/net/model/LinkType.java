package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("link_type")
public class LinkType {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "网站类型ID")
    private Integer id;
    @ApiModelProperty(value = "网站类型名称", example = "技术类", required = true)
    private String name;
}
