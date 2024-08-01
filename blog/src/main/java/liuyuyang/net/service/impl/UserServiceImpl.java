package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.UserMapper;
import liuyuyang.net.model.User;
import liuyuyang.net.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Page<User> list(Integer page, Integer size) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<User> result = new Page<>(page, size);
        userMapper.selectPage(result, queryWrapper);

        return result;
    }
}