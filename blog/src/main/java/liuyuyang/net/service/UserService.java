package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.dto.user.EditPassDTO;
import liuyuyang.net.dto.user.UserInfoDTO;
import liuyuyang.net.model.User;
import liuyuyang.net.result.Result;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.user.UserFillterVo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserService extends IService<User> {
    public User get(Integer id);
    public void add(User data);
    public void del(Integer id);
    public void edit(UserInfoDTO data);
    public List<User> list(UserFillterVo filterVo);
    public Page<User> paging(UserFillterVo filterVo, PageVo pageVo);

    public User login(User user);
    public void editPass(EditPassDTO data);
}
