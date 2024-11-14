package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.dto.user.EditPassDTO;
import liuyuyang.net.dto.user.UserDTO;
import liuyuyang.net.dto.user.UserInfoDTO;
import liuyuyang.net.dto.user.UserLoginDTO;
import liuyuyang.net.model.User;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.user.UserFillterVo;

import java.util.List;

public interface UserService extends IService<User> {
    public User get(Integer id);
    public void add(UserDTO data);
    public void del(Integer id);
    public void delBatch(List<Integer> ids);
    public void edit(UserInfoDTO data);
    public List<User> list(UserFillterVo filterVo);
    public Page<User> paging(UserFillterVo filterVo, PageVo pageVo);

    public User login(UserLoginDTO user);
    public void editPass(EditPassDTO data);
}
