package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.RouteRoleMapper;
import liuyuyang.net.model.RouteRole;
import liuyuyang.net.service.RouteRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RouteRoleServiceImpl extends ServiceImpl<RouteRoleMapper, RouteRole> implements RouteRoleService {

}