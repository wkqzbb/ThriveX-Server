package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName("footprint")
public class Footprint {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "标题", example = "这是一个标题", required = true)
    private String title;
    @ApiModelProperty(value = "地址", example = "这是一个地址", required = true)
    private String address;
    @ApiModelProperty(value = "内容", example = "这是一段内容", required = true)
    private String content;
    @ApiModelProperty(value = "坐标纬度", example = "[\"113.625368, 34.746599\"]", required = true)
    private List<Integer> position;
    @ApiModelProperty(value = "图片", example = "这是一张图片")
    private List<String> image;
    @ApiModelProperty(value = "时间", example = "1723533206613", required = true)
    private String time;
}
