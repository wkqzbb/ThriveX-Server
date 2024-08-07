package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Cate;
import liuyuyang.net.model.Comment;
import liuyuyang.net.model.Tag;
import liuyuyang.net.service.ArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Article get(Integer id) {
        Article data = articleMapper.selectById(id);
        data.setCateList(articleMapper.getCateList(id));
        data.setTagList(articleMapper.getTagList(id));
        data.setComment(commentMapper.getCommentList(id).size());
        return data;
    }

    @Override
    public List<Article> list() {
        List<Article> data = articleMapper.selectList(null);

        for (Article article : data) {
            // 查询该文章下所有绑定的分类和标签以及评论数量
            List<Cate> cateList = articleMapper.getCateList(article.getId());
            article.setCateList(cateList);

            List<Tag> tagList = articleMapper.getTagList(article.getId());
            article.setTagList(tagList);

            article.setComment(commentMapper.getCommentList(article.getId()).size());
        }

        return data;
    }

    @Override
    public Page<Article> paging(Integer page, Integer size) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Article> result = new Page<>(page, size);

        articleMapper.selectPage(result, queryWrapper);

        for (Article article : result.getRecords()) {
            // 查询该文章下所有绑定的分类和标签
            List<Cate> cateList = articleMapper.getCateList(article.getId());
            article.setCateList(cateList);

            List<Tag> tagList = articleMapper.getTagList(article.getId());
            article.setTagList(tagList);

            article.setComment(commentMapper.getCommentList(article.getId()).size());
        }

        return result;
    }
}