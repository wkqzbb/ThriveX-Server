package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.User;

public interface UserService extends IService<User> {
    public Page<User> list(Integer page, Integer size);
}
