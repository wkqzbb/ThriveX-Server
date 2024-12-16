package liuyuyang.net.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liuyuyang.net.model.FilePartDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilePartDetailMapper extends BaseMapper<FilePartDetail> {
    // 自定义SQL方法可以在这里定义
}