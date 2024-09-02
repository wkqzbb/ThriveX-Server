package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Comment;
import liuyuyang.net.service.CommentService;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.comment.CommentFilterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static liuyuyang.net.utils.YuYangUtils.getPageObject;

@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Comment get(Integer id) {
        Comment data = commentMapper.selectById(id);

        if (data == null) {
            throw new CustomException(400, "该评论不存在");
        }

        // 绑定数据
        bindingData(data);

        // 获取当前评论下的所有子评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", id);
        List<Comment> list = commentMapper.selectList(queryWrapper);
        data.setChildren(buildCommentTree(list, id));

        return data;
    }

    @Override
    public Page getCommentList(Integer aid, PageVo pageVo) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", aid);
        queryWrapper.eq("audit_status", 1);
        queryWrapper.orderByDesc("create_time");

        Page<Comment> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        commentMapper.selectPage(page, queryWrapper);

        List<Comment> list = page.getRecords();
        list.forEach(this::bindingData); // 使用公共方法

        // 构建评论树
        list = buildCommentTree(list, 0);

        // 分页处理
        return getPageObject(pageVo, list); // 使用公共方法
    }

    @Override
    public List<Comment> list(CommentFilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Comment> queryWrapper = queryWrapperComment(filterVo, sortVo);
        List<Comment> list = commentMapper.selectList(queryWrapper);

        // 查询的结构格式
        if (Objects.equals(filterVo.getPattern(), "list")) return list;

        // 绑定相关数据
        list.forEach(this::bindingData); // 使用公共方法

        // 构建多级评论
        return buildCommentTree(list, 0);
    }

    @Override
    public Page<Comment> paging(CommentFilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        List<Comment> list = list(filterVo, sortVo);

        // return getPageComments(pageVo, list);
        return getPageObject(pageVo, list);
    }

    // 绑定对应的数据
    private void bindingData(Comment comment) {
        Article article = articleMapper.selectById(comment.getArticleId());
        if (article != null) {
            comment.setArticleTitle(article.getTitle());
        }
    }

    // 分页查询逻辑
    // private Page<Comment> getPageComments(PageVo pageVo, List<Comment> comments) {
    //     int start = (pageVo.getPage() - 1) * pageVo.getSize();
    //     int end = Math.min(start + pageVo.getSize(), comments.size());
    //     List<Comment> pagedComments = comments.subList(start, end);
    //
    //     Page<Comment> result = new Page<>(pageVo.getPage(), pageVo.getSize());
    //     result.setRecords(pagedComments);
    //     result.setTotal(comments.size());
    //
    //     return result;
    // }

    // 递归构建评论列表
    private List<Comment> buildCommentTree(List<Comment> list, Integer cid) {
        List<Comment> children = new ArrayList<>();

        for (Comment comment : list) {
            if (comment.getCommentId().equals(cid)) {
                comment.setChildren(buildCommentTree(list, comment.getId()));
                children.add(comment);
            }

            bindingData(comment);
        }
        return children;
    }

    // 过滤评论数据
    // @Override
    public QueryWrapper<Comment> queryWrapperComment(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        // 根据发布时间从早到晚排序
        switch (sortVo.getSort()) {
            case "asc":
                queryWrapper.orderByAsc("create_time");
                break;
            case "desc":
                queryWrapper.orderByDesc("create_time");
                break;
        }

        // 根据关键字通过标题过滤出对应数据
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like("content", "%" + filterVo.getKey() + "%");
        }

        // 根据开始与结束时间过滤
        if (filterVo.getStartDate() != null && filterVo.getEndDate() != null) {
            queryWrapper.between("create_time", filterVo.getStartDate(), filterVo.getEndDate());
        } else if (filterVo.getStartDate() != null) {
            queryWrapper.ge("create_time", filterVo.getStartDate());
        } else if (filterVo.getEndDate() != null) {
            queryWrapper.le("create_time", filterVo.getEndDate());
        }

        return queryWrapper;
    }
}