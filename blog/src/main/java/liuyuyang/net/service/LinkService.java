package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Link;

public interface LinkService extends IService<Link> {
    public Page<Link> list(Integer page, Integer size);
}
