package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "评论ID")
    private Integer id;

    @ApiModelProperty(value = "评论者名称", example = "宇阳", required = true)
    private String name;

    @ApiModelProperty(value = "评论者头像", example = "yuyang.jpg")
    private String avatar;

    @ApiModelProperty(value = "评论者邮箱", example = "liuyuyang1024@yeah.net")
    private String email;

    @ApiModelProperty(value = "评论者网站", example = "https://blog.liuyuyang.net")
    private String url;

    @ApiModelProperty(value = "评论内容", example = "这是一段评论内容", required = true)
    private String content;

    @ApiModelProperty(value = "该评论所绑定的文章ID", example = "1", required = true)
    private Integer articleId;

    @ApiModelProperty(value = "二级评论", example = "2", required = true)
    private Integer commentId;

    @TableField(exist = false)
    private List<Comment> children = new ArrayList<>();

    @ApiModelProperty(value = "评论是否审核通过", example = "1")
    private Integer auditStatus;

    @ApiModelProperty(value = "评论创建时间", example = "2024-03-22 10:00:00")
    private Date createTime;
}
