package liuyuyang.net.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class FilterVo {
    @ApiModelProperty(value = "根据关键词进行模糊查询")
    private String key;

    // @ApiParam(value = "根据分类进行查询")
    // String cateIds;
    // @ApiParam(value = "根据标签进行模糊查询")
    // String tagIds;

    @ApiParam(value = "根据开始时间进行查询")
    private Integer startDate;
    @ApiParam(value = "根据结束时间进行查询")
    private Integer endDate;
}
