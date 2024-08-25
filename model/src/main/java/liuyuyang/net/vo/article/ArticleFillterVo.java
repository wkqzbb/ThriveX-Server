package liuyuyang.net.vo.article;

import io.swagger.annotations.ApiModelProperty;
import liuyuyang.net.vo.FilterVo;
import lombok.Data;

import java.util.List;

@Data
public class ArticleFillterVo extends FilterVo {
    @ApiModelProperty(value = "根据分类进行查询")
    private List<Integer> cateIds;
    @ApiModelProperty(value = "根据标签进行模糊查询")
    private String tagId;
}
