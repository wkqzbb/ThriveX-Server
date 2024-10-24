package liuyuyang.net.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Link;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.link.LinkFilterVo;

import java.util.List;

public interface LinkService extends IService<Link> {
    public void add(Link link, String token) throws Exception;

    public Link get(Integer cid);

    public List<Link> list(LinkFilterVo filterVo);

    public Page<Link> paging(LinkFilterVo filterVo, PageVo pageVo);
}
