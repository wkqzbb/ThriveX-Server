package liuyuyang.net.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
public class FilterVo {
    @ApiModelProperty(value = "根据关键词进行模糊查询")
    private String key;

    @ApiParam(value = "根据分类进行查询")
    private List<Integer> cateIds;
    @ApiParam(value = "根据标签进行模糊查询")
    private String tagId;

    @ApiParam(value = "根据开始时间进行查询")
    private String startDate;
    @ApiParam(value = "根据结束时间进行查询")
    private String endDate;
}
