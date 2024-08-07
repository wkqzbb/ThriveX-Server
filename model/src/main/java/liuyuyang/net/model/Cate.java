package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cate")
public class Cate {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "分类ID")
    private Integer id;
    @ApiModelProperty(value = "分类名称", example = "大前端", required = true)
    private String name;
    @ApiModelProperty(value = "分类链接", example = "/")
    private String url;
    @ApiModelProperty(value = "分类标识", example = "dqd", required = true)
    private String mark;
    @ApiModelProperty(value = "分类图标", example = "🎉")
    private String icon;
    @ApiModelProperty(value = "分类级别", example = "0", required = true)
    private Integer level;
    @TableField(exist = false)
    private List<Cate> children = new ArrayList<>();
}
