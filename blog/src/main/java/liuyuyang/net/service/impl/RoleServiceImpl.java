package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.RoleMapper;
import liuyuyang.net.mapper.RouteRoleMapper;
import liuyuyang.net.model.Role;
import liuyuyang.net.model.Route;
import liuyuyang.net.model.RouteRole;
import liuyuyang.net.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RouteRoleMapper routeRoleMapper;

    @Override
    public List<Route> getRouteList(Integer id) {
        return roleMapper.getRouteList(id);
    }

    @Override
    public void bindingRoute(Integer roleId, List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CustomException(400, "请确保参数完整");
        }

        // 先删除当前角色绑定的所有路由
        QueryWrapper<RouteRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        routeRoleMapper.delete(queryWrapper);

        // 然后重新给角色绑定所有路由
        for (Integer routeId : ids) {
            RouteRole routeRole = new RouteRole();
            routeRole.setRoleId(roleId);
            routeRole.setRouteId(routeId);
            routeRoleMapper.insert(routeRole);
        }
    }
}