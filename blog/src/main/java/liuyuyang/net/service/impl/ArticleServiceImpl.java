package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.ArticleCateMapper;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.mapper.CateMapper;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.ArticleCate;
import liuyuyang.net.model.Cate;
import liuyuyang.net.service.ArticleService;
import liuyuyang.net.service.CateService;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.article.ArticleFillterVo;
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
    private ArticleCateMapper articleCateMapper;
    @Resource
    private CateMapper cateMapper;
    @Resource
    private CateService cateService;
    @Resource
    private CommentMapper commentMapper;

    @Override
    public void add(Article article) {
        articleMapper.insert(article);

        for (Integer id : article.getCateIds()) {
            ArticleCate articleCate = new ArticleCate();
            articleCate.setArticleId(article.getId());
            articleCate.setCateId(id);
            articleCateMapper.insert(articleCate);
        }
    }

    @Override
    public void edit(Article article) {
        articleMapper.updateById(article);

        // 先删除之前绑定的分类
        articleCateMapper.deleteBatchIds(article.getCateIds());
        // 再重新绑定分类
        for (Integer id : article.getCateIds()) {
            ArticleCate articleCate = new ArticleCate();
            articleCate.setArticleId(article.getId());
            articleCate.setCateId(id);
            articleCateMapper.insert(articleCate);
        }
    }

    @Override
    public Article get(Integer id) {
        Article data = articleMapper.selectById(id);

        // 查询当前文章的分类ID
        QueryWrapper<ArticleCate> queryWrapperCateIds = new QueryWrapper<>();
        queryWrapperCateIds.eq("article_id", id);
        List<Integer> cids = articleCateMapper.selectList(queryWrapperCateIds).stream().map(ArticleCate::getCateId).collect(Collectors.toList());

        // 如果有分类，则绑定分类信息
        if (!cids.isEmpty()) {
            QueryWrapper<Cate> queryWrapperCateList = new QueryWrapper<>();
            queryWrapperCateList.in("id", cids);
            List<Cate> cates = cateService.buildCateTree(cateMapper.selectList(queryWrapperCateList), 0);
            data.setCateList(cates);
        }

        data.setCateIds(cids);
        data.setTagList(articleMapper.getTagList(id));
        data.setComment(commentMapper.getCommentList(id).size());
        return data;
    }

    @Override
    public List<Article> list(ArticleFillterVo filterVo, SortVO sortVo) {
        QueryWrapper<Article> queryWrapper = queryWrapperArticle(filterVo, sortVo);
        List<Article> list = articleMapper.selectList(queryWrapper);
        return list.stream().map(article -> get(article.getId())).collect(Collectors.toList());
    }

    @Override
    public Page<Article> paging(ArticleFillterVo filterVo, SortVO sortVo, PageVo pageVo) {
        QueryWrapper<Article> queryWrapper = queryWrapperArticle(filterVo, sortVo);
        Page<Article> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        articleMapper.selectPage(page, queryWrapper);
        page.setRecords(page.getRecords().stream().map(article -> get(article.getId())).collect(Collectors.toList()));
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

    // 过滤文章数据
    private QueryWrapper<Article> queryWrapperArticle(ArticleFillterVo filterVo, SortVO sortVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();

        // 根据发布时间从早到晚排序
        switch (sortVo.getSort()) {
            case "asc":
                queryWrapper.orderByAsc("create_time");
                break;
            case "desc":
                queryWrapper.orderByDesc("create_time");
                break;
        }

        // 根据关键字通过标题过滤出对应文章数据
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like("title", "%" + filterVo.getKey() + "%");
        }

        // 根据开始与结束时间过滤
        if (filterVo.getStartDate() != null && filterVo.getEndDate() != null) {
            queryWrapper.between("create_time", filterVo.getStartDate(), filterVo.getEndDate());
        } else if (filterVo.getStartDate() != null) {
            queryWrapper.ge("create_time", filterVo.getStartDate());
        } else if (filterVo.getEndDate() != null) {
            queryWrapper.le("create_time", filterVo.getEndDate());
        }

        // 根据分类id过滤
        if (filterVo.getCateIds() != null && !filterVo.getCateIds().isEmpty()) {
            QueryWrapper<ArticleCate> queryWrapperArticleIds = new QueryWrapper<>();
            queryWrapperArticleIds.in("cate_id", filterVo.getCateIds());
            List<Integer> articleIds = articleCateMapper.selectList(queryWrapperArticleIds).stream().map(ArticleCate::getArticleId).collect(Collectors.toList());

            if (!articleIds.isEmpty()) {
                queryWrapper.in("id", articleIds);
            } else {
                // 添加一个始终为假的条件
                queryWrapper.in("id", -1); // -1 假设为不存在的ID
            }
        }

        // 根据标签id过滤
        if (filterVo.getTagId() != null && !filterVo.getTagId().isEmpty()) {
            queryWrapper.like("tag_ids", "%" + filterVo.getTagId() + "%");
        }

        return queryWrapper;
    }
}