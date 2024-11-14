package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.dto.user.EditPassDTO;
import liuyuyang.net.dto.user.UserDTO;
import liuyuyang.net.dto.user.UserInfoDTO;
import liuyuyang.net.dto.user.UserLoginDTO;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.RoleMapper;
import liuyuyang.net.mapper.UserMapper;
import liuyuyang.net.model.*;
import liuyuyang.net.service.UserService;
import liuyuyang.net.utils.YuYangUtils;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.user.UserFillterVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private YuYangUtils yuYangUtils;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Override
    public void add(UserDTO user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());

        User data = userMapper.selectOne(queryWrapper);

        // 判断用户是否存在
        if (data != null) throw new CustomException(400, "该用户已存在：" + user.getUsername());

        // 密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        User temp = new User();
        BeanUtils.copyProperties(user, temp);

        userMapper.insert(temp);
    }

    @Override
    public void del(Integer id) {
        User data = userMapper.selectById(id);
        if (data == null) throw new CustomException(400, "该用户不存在");
    }

    @Override
    public void delBatch(List<Integer> ids) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        userMapper.delete(queryWrapper);
    }

    @Override
    public void edit(UserInfoDTO user) {
        User data = userMapper.selectById(user.getId());
        BeanUtils.copyProperties(user, data);
        userMapper.updateById(data);
    }

    @Override
    public User get(Integer id) {
        User data = userMapper.selectById(id);
        data.setPassword("只有聪明的人才能看到密码");

        Role role = roleMapper.selectById(data.getRoleId());
        data.setRole(role);

        return data;
    }

    @Override
    public List<User> list(UserFillterVo filterVo) {
        QueryWrapper<User> queryWrapper = yuYangUtils.queryWrapperFilter(filterVo);

        List<User> list = userMapper.selectList(queryWrapper);

        for (User user : list) {
            user.setPassword("只有聪明的人才能看到密码");
            Role role = roleMapper.selectById(user.getRoleId());
            user.setRole(role);
        }

        list = list.stream().sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());

        return list;
    }

    @Override
    public Page<User> paging(UserFillterVo filterVo, PageVo pageVo) {
        List<User> list = list(filterVo);
        return yuYangUtils.getPageData(pageVo, list);
    }

    @Override
    public User login(UserLoginDTO user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));

        User data = userMapper.selectOne(queryWrapper);

        if (data == null) throw new CustomException(400, "用户名或密码错误");

        return data;
    }

    @Override
    public void editPass(EditPassDTO data) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", data.getUsername());
        queryWrapper.eq("password", DigestUtils.md5DigestAsHex(data.getOldPassword().getBytes()));

        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new CustomException(400, "用户名或旧密码错误");
        }

        user.setPassword(DigestUtils.md5DigestAsHex(data.getNewPassword().getBytes()));
        userMapper.updateById(user);
    }
}