package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Article;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.article.ArticleFillterVo;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public void add(Article article);
    public void edit(Article article);
    public Article get(Integer id);
    public List<Article> list(ArticleFillterVo filterVo, SortVO sortVo);
    public Page<Article> paging(ArticleFillterVo filterVo, SortVO sortVo, PageVo pageVo);
    public List<Article> getArticleList(Integer id, SortVO sortVo, PageVo pageVo);
    public List<Article> getRandomArticles(Integer count);
    public List<Article> getRecommendedArticles(Integer count);
}
