package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Article;

public interface ArticleService extends IService<Article> {
    public Page<Article> paging(Integer page, Integer size);
}
