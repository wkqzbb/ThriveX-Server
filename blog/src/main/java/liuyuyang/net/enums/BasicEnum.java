package liuyuyang.net.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * 字典基础接口
 *
 * @author laifeng
 */
public interface BasicEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 获取描述
     */
    String getDesc();

}