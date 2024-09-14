package liuyuyang.net.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Link;
import liuyuyang.net.model.Link;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.article.ArticleFillterVo;

import java.util.List;

public interface LinkService extends IService<Link> {
    public void add(Link link, String token);

    public Link get(Integer cid);

    public List<Link> list(FilterVo filterVo, SortVO sortVo);

    public Page<Link> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo);

    public QueryWrapper<Link> queryWrapperArticle(FilterVo filterVo, SortVO sortVo);
}
