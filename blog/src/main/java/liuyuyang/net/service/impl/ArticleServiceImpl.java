package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<Article> list(Integer page, Integer size) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Article> result = new Page<>(page, size);
        articleMapper.selectPage(result, queryWrapper);

        return result;
    }
}