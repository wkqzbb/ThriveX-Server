package liuyuyang.net.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PageVo {
    @ApiParam(value = "默认为第1页")
    Integer page = 1;
    @ApiParam(value = "默认为每页显示5个数据")
    Integer size = 5;
}
