package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("link")
public class Link {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "网站ID")
    private Integer id;
    @ApiModelProperty(value = "网站标题", example = "这是一个网站", required = true)
    private String title;
    @ApiModelProperty(value = "网站描述", example = "这是一个网站的描述")
    private String description;
    @ApiModelProperty(value = "网站邮箱", example = "liuyuyang1024@yeah.net")
    private String email;
    @ApiModelProperty(value = "网站类型", example = "技术类", required = true)
    private String type;
    @ApiModelProperty(value = "网站图片", example = "http://127.0.0.1:5000/1.jpg", required = true)
    private String image;
    @ApiModelProperty(value = "网站链接", example = "/", required = true)
    private String url;
    @ApiModelProperty(value = "网站创建时间", example = "2024-07-25 13:21:55", required = true)
    private String createTime;
}
