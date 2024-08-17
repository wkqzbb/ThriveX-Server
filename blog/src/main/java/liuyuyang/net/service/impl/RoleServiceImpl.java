package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.RoleMapper;
import liuyuyang.net.model.Role;
import liuyuyang.net.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}