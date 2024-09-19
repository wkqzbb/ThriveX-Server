package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("wall")
public class Wall extends BaseModel {
    @ApiModelProperty(value = "留言人名称", example = "神秘人")
    private String name;
    @TableField(exist = false)
    @ApiModelProperty(value = "留言分类", example = "全部")
    private String cate;
    @ApiModelProperty(value = "留言墙颜色", example = "#92e6f54d", required = true)
    private String color;
    @ApiModelProperty(value = "留言内容", example = "这是一段内容", required = true)
    private String content;
}
