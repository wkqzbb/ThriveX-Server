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
@TableName(value = "footprint", autoResultMap = true)
public class Footprint {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "标题", example = "这是一个标题", required = true)
    private String title;
    @ApiModelProperty(value = "地址", example = "这是一个地址", required = true)
    private String address;
    @ApiModelProperty(value = "内容", example = "这是一段内容")
    private String content;
    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "坐标纬度", example = "[]", required = true)
    private List<Integer> position;
    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "图片", example = "[]")
    private List<String> image;
    @ApiModelProperty(value = "时间", example = "1723533206613", required = true)
    private String time;
}
