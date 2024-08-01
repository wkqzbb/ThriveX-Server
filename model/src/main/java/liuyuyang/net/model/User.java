package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "用户ID")
    private Integer id;

    @ApiModelProperty(value = "用户账号", example = "liuyuyang", required = true)
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户名称", example = "宇阳", required = true)
    private String name;

    @ApiModelProperty(value = "用户介绍", example = "再渺小的星光，也有属于他的光芒!")
    private String info;

    @ApiModelProperty(value = "用户邮箱", example = "liuyuyang1024@yeah.net")
    private String email;

    @ApiModelProperty(value = "用户角色", example = "admin")
    private String role;

    @ApiModelProperty(value = "用户创建时间", example = "2024-08-22 10:00:00")
    private Date create_time;
}
