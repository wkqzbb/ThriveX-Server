package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Role;
import liuyuyang.net.model.Route;

import java.util.List;

public interface RoleService extends IService<Role> {
    public List<Route> getRouteList(Integer id);
}
