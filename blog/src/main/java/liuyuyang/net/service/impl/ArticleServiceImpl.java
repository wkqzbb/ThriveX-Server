package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Cate;
import liuyuyang.net.model.Tag;
import liuyuyang.net.service.ArticleService;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Article> list(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like("title", "%" + filterVo.getKey() + "%");
        }

        if (filterVo.getStartDate() != null && filterVo.getEndDate() != null) {
            queryWrapper.between("create_time", filterVo.getStartDate(), filterVo.getEndDate());
        } else if (filterVo.getStartDate() != null) {
            queryWrapper.ge("create_time", filterVo.getStartDate());
        } else if (filterVo.getEndDate() != null) {
            queryWrapper.le("create_time", filterVo.getEndDate());
        }

        if (filterVo.getCateIds() != null && !filterVo.getCateIds().isEmpty()) {
            queryWrapper.in("cate_ids", filterVo.getCateIds());
        }

        if (filterVo.getTagId() != null && !filterVo.getTagId().isEmpty()) {
            queryWrapper.like("tag_ids", "%" + filterVo.getTagId() + "%");
        }

        List<Article> list = articleMapper.selectList(queryWrapper);

        Stream<Integer> ids = list.stream().map(Article::getId);
        list = ids.map(id -> get(id)).collect(Collectors.toList());

        // for (Article article : list) {
        //     // 查询该文章下所有绑定的分类和标签以及评论数量
        //     List<Cate> cateList = articleMapper.getCateList(article.getId());
        //     article.setCateList(cateList);
        //
        //     List<Tag> tagList = articleMapper.getTagList(article.getId());
        //     article.setTagList(tagList);
        //
        //     article.setComment(commentMapper.getCommentList(article.getId()).size());
        // }

        switch (sortVo.getSort()) {
            case "asc":
                list.sort((a1, a2) -> a1.getCreateTime().compareTo(a2.getCreateTime()));
                break;
            case "desc":
                list.sort((a1, a2) -> a2.getCreateTime().compareTo(a1.getCreateTime()));
                break;
        }

        return list;
    }

    @Override
    public Page<Article> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        switch (sortVo.getSort()) {
            case "asc":
                queryWrapper.orderByAsc("create_time");
                break;
            case "desc":
                queryWrapper.orderByDesc("create_time");
                break;
        }

        // 分页查询
        Page<Article> page = new Page<>(pageVo.getPage(), pageVo.getSize());

        articleMapper.selectPage(page, queryWrapper);

        Stream<Integer> ids = page.getRecords().stream().map(Article::getId);
        page.setRecords(ids.map(id -> get(id)).collect(Collectors.toList()));

        return page;
    }

    @Override
    public List<Article> getArticleList(Integer id, SortVO sortVo, PageVo pageVo) {
        List<Article> list = articleMapper.getArticleList(id);
        Stream<Integer> ids = list.stream().map(Article::getId);
        list = ids.map(this::get).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<Article> getRandomArticles(Integer count) {
        List<Integer> ids = articleMapper.selectList(null).stream().map(Article::getId).collect(Collectors.toList());

        if (ids.size() <= count) {
            // 如果文章数量少于或等于需要的数量，直接返回所有文章
            return ids.stream()
                    .map(this::get)
                    .collect(Collectors.toList());
        }

        // 随机打乱文章ID列表
        Collections.shuffle(ids, new Random());

        // 选择前 count 个文章ID
        List<Integer> randomArticleIds = ids.subList(0, count);

        // 根据随机选择的文章ID获取文章
        return randomArticleIds.stream()
                .map(this::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> getRecommendedArticles(Integer count) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view").last("LIMIT " + count);
        return this.list(queryWrapper);
    }
}