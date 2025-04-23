package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("album_cate_image")
public class AlbumCateImage {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "分类 ID", example = "1", required = true)
    private Integer cateId;

    @ApiModelProperty(value = "相册 ID", example = "1", required = true)
    private Integer imageId;
}
