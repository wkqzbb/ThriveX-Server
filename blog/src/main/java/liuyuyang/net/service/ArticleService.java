package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Article;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.OrderVO;
import liuyuyang.net.vo.PageVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public Article get(Integer id);

    public List<Article> list(FilterVo filterVo, OrderVO orderVo);

    public Page<Article> paging(FilterVo filterVo, OrderVO orderVo, PageVo pageVo);
}
