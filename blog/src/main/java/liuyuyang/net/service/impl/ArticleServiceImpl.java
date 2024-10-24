package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
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
import liuyuyang.net.vo.article.ArticleFillterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    public void del(Integer id) {
        // 先删除之前绑定的分类
        QueryWrapper<ArticleCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("article_id", id);
        articleCateMapper.delete(queryWrapper);

        // 再删除当前文章
        int res = articleMapper.deleteById(id);
        if (res == 0) throw new CustomException(400, "删除文章失败");
    }

    @Override
    public void delBatch(List<Integer> ids) {
        // 先删除之前绑定的分类
        for (Integer id : ids) {
            QueryWrapper<ArticleCate> queryWrapperArticleCate = new QueryWrapper<>();
            queryWrapperArticleCate.in("article_id", id);
            articleCateMapper.delete(queryWrapperArticleCate);
        }

        // 再批量删除文章
        QueryWrapper<Article> queryWrapperArticle = new QueryWrapper<>();
        queryWrapperArticle.in("id", ids);
        int res = articleMapper.delete(queryWrapperArticle);
        if (res == 0) throw new CustomException(400, "批量删除文章失败");
    }

    @Override
    public void edit(Article article) {
        if (article.getCateIds() == null) throw new CustomException(400, "编辑失败：请绑定分类");

        // 先删除之前绑定的分类
        QueryWrapper<ArticleCate> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("article_id", article.getId());
        articleCateMapper.delete(queryWrapper);

        // 再重新绑定分类
        for (Integer id : article.getCateIds()) {
            ArticleCate articleCate = new ArticleCate();
            articleCate.setArticleId(article.getId());
            articleCate.setCateId(id);
            articleCateMapper.insert(articleCate);
        }

        articleMapper.updateById(article);
    }

    @Override
    public Article get(Integer id) {
        Article data = bindingData(id);

        // 获取当前文章的创建时间
        String createTime = data.getCreateTime();

        // 查询上一篇文章
        QueryWrapper<Article> prevQueryWrapper = new QueryWrapper<>();
        prevQueryWrapper.lt("create_time", createTime).orderByDesc("create_time").last("LIMIT 1");
        Article prevArticle = articleMapper.selectOne(prevQueryWrapper);
        if (prevArticle != null) {
            Map<String, Object> resultPrev = new HashMap<>();
            resultPrev.put("id", prevArticle.getId());
            resultPrev.put("title", prevArticle.getTitle());
            data.setPrev(resultPrev);
        }

        // 查询下一篇文章
        QueryWrapper<Article> nextQueryWrapper = new QueryWrapper<>();
        nextQueryWrapper.gt("create_time", createTime).orderByAsc("create_time").last("LIMIT 1");
        Article nextArticle = articleMapper.selectOne(nextQueryWrapper);
        if (nextArticle != null) {
            Map<String, Object> resultNext = new HashMap<>();
            resultNext.put("id", nextArticle.getId());
            resultNext.put("title", nextArticle.getTitle());
            data.setNext(resultNext);
        }

        return data;
    }

    @Override
    public List<Article> list(ArticleFillterVo filterVo) {
        QueryWrapper<Article> queryWrapper = queryWrapperArticle(filterVo);
        List<Article> list = articleMapper.selectList(queryWrapper);
        return list.stream().map(article -> bindingData(article.getId())).collect(Collectors.toList());
    }

    @Override
    public Page<Article> paging(ArticleFillterVo filterVo, PageVo pageVo) {
        QueryWrapper<Article> queryWrapper = queryWrapperArticle(filterVo);
        Page<Article> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        articleMapper.selectPage(page, queryWrapper);
        page.setRecords(page.getRecords().stream().map(article -> bindingData(article.getId())).collect(Collectors.toList()));
        return page;
    }

    @Override
    public Page<Article> getArticleList(Integer id, PageVo pageVo) {
        // 先通过分类id查询出所有文章id
        QueryWrapper<ArticleCate> queryWrapperArticleCate = new QueryWrapper<>();
        queryWrapperArticleCate.in("cate_id", id);
        List<Integer> articleIds = articleCateMapper.selectList(queryWrapperArticleCate).stream().map(ArticleCate::getArticleId).collect(Collectors.toList());

        // 然后通过文章的id查询出对应的文章数据
        QueryWrapper<Article> queryWrapperArticle = new QueryWrapper<>();
        queryWrapperArticle.orderByDesc("create_time");

        // 有数据就查询，没有就返回空数组
        if (!articleIds.isEmpty()) {
            queryWrapperArticle.in("id", articleIds);
        } else {
            queryWrapperArticle.in("id", -1);
        }

        Page<Article> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        articleMapper.selectPage(page, queryWrapperArticle);
        page.setRecords(page.getRecords().stream().map(article -> bindingData(article.getId())).collect(Collectors.toList()));

        return page;
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
                .map(this::bindingData)
                .collect(Collectors.toList());
    }

    @Override
    public List<Article> getRecommendedArticles(Integer count) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view").last("LIMIT " + count);
        return list(queryWrapper);
    }

    @Override
    public void recordView(Integer id) {
        Article data = articleMapper.selectById(id);
        if (data == null) throw new CustomException(400, "获取失败：该文章不存在");
        data.setView(data.getView() + 1);
        articleMapper.updateById(data);
    }

    // 关联文章数据
    @Override
    public Article bindingData(Integer id) {
        Article data = articleMapper.selectById(id);
        if (data == null) throw new CustomException(400, "获取文章失败：该文章不存在");

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

        data.setTagList(articleMapper.getTagList(id));
        data.setComment(commentMapper.getCommentList(id).size());
        return data;
    }

    // 过滤文章数据
    @Override
    public QueryWrapper<Article> queryWrapperArticle(ArticleFillterVo filterVo) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

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
        if (filterVo.getTagId() != null) {
            queryWrapper.like("tag_ids", "%" + filterVo.getTagId() + "%");
        }

        return queryWrapper;
    }
}