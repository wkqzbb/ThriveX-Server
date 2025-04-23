package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "album", autoResultMap = true)
public class Album {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "相册名称", example = "旅行", required = true)
    private String name;

    @ApiModelProperty(value = "文章封面链接", example = "http://123.com/images/example.jpg")
    private String cover;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "相册列表", example = "[]")
    private List<String> images;
}
