package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("article_config")
public class ArticleConfig {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "文章是否置顶", example = "0")
    private Integer top;

    @ApiModelProperty(value = "文章状态", example = "显示（show） 不在首页显示（no_home） 隐藏（hide） 私密（private）")
    private String status;

    @ApiModelProperty(value = "文章ID", example = "1", required = true)
    private Integer articleId;
}
