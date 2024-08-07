package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {
    public Article get(Integer id);
    public List<Article> list();
    public Page<Article> paging(Integer page, Integer size);
}
