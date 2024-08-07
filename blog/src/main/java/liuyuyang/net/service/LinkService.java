package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Link;
import liuyuyang.net.model.Link;

import java.util.List;

public interface LinkService extends IService<Link> {
    public Link get(Integer cid);

    public List<Link> list();

    public Page<Link> paging(Integer page, Integer size);
}
