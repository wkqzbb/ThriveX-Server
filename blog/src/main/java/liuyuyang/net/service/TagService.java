package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Tag;

public interface TagService extends IService<Tag> {
    public Page<Tag> list(Integer page, Integer size);
}
