package liuyuyang.net.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liuyuyang.net.model.Role;
import liuyuyang.net.model.Route;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    // 查询指定角色的所有路由权限
    @Select("select a.* from route a, role b, route_role c where c.route_id = a.id and c.role_id = b.id and b.id = #{id}")
    public List<Route> getRouteList(Integer id);
}
