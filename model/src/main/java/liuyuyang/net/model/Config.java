package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import liuyuyang.net.dto.Layout;
import lombok.Data;

@Data
@TableName("config")
public class Config extends Layout {
    @TableId(type = IdType.AUTO)
    private Integer id;
}