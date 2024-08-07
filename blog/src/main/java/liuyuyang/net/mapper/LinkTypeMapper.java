package liuyuyang.net.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liuyuyang.net.model.LinkType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Type;

@Mapper
public interface LinkTypeMapper extends BaseMapper<LinkType> {
    @Select("select * from link l, link_type lt where l.type_id = lt.id && l.id = #{lid}")
    public LinkType getTypeData(Integer lid);
}
