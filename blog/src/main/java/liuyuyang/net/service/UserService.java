package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.dto.user.EditPassDTO;
import liuyuyang.net.model.User;

public interface UserService extends IService<User> {
    public User get(Integer id);
    public void editPass(EditPassDTO data);

    public Page<User> paging(Integer page, Integer size);

    public void register(User user);

    public User login(User user);
}
