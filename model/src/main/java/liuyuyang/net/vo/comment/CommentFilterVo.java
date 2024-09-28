package liuyuyang.net.vo.comment;

import io.swagger.annotations.ApiModelProperty;
import liuyuyang.net.vo.FilterVo;
import lombok.Data;

@Data
public class CommentFilterVo extends FilterVo {
    @ApiModelProperty(value = "默认为树形结构，如果设置了list模式，则查询列表结构")
    private String pattern;

    @ApiModelProperty(value = "根据内容关键词过滤评论")
    private String content;
}
