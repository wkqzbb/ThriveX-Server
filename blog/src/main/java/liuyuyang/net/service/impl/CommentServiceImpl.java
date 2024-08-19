package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.ArticleMapper;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Cate;
import liuyuyang.net.model.Comment;
import liuyuyang.net.model.Comment;
import liuyuyang.net.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        // 文章标题
        Article article = articleMapper.selectById(data.getArticleId());
        data.setArticleTitle(article.getTitle());

        // 获取当前评论下的所有子评论
        List<Comment> comments = commentMapper.selectList(null);
        data.setChildren(buildCommentTree(comments, id));

        return data;
    }

    @Override
    public List<Comment> getCommentList(Integer aid) {
        // 查询所有评论
        List<Comment> list = commentMapper.getCommentList(aid);

        for (Comment comment : list) {
            Article article = articleMapper.selectById(comment.getArticleId());
            comment.setArticleTitle(article.getTitle());
        }

        // 构建评论树
        List<Comment> result = buildCommentTree(list, 0);
        return result;
    }

    @Override
    public List<Comment> list(String pattern) {
        // 查询所有分类
        List<Comment> list = commentMapper.selectList(null);

        // 如果参数是list则直接不进行递归处理
        if (Objects.equals(pattern, "list")) return list;

        for (Comment comment : list) {
            Article article = articleMapper.selectById(comment.getArticleId());
            comment.setArticleTitle(article.getTitle());
        }

        // 构建评论树
        List<Comment> result = buildCommentTree(list, 0);

        return result;
    }

    @Override
    public Page<Comment> paging(Integer page, Integer size) {
        // 查询所有评论
        List<Comment> list = commentMapper.selectList(new QueryWrapper<>());

        for (Comment comment : list) {
            Article article = articleMapper.selectById(comment.getArticleId());
            comment.setArticleTitle(article.getTitle());
        }

        // 构建评论树
        List<Comment> comments = buildCommentTree(list, 0);

        // 分页处理
        int start = (page - 1) * size;
        int end = Math.min(start + size, comments.size());
        List<Comment> pagedComments = comments.subList(start, end);

        // 返回分页结果
        Page<Comment> result = new Page<>(page, size);
        result.setRecords(pagedComments);
        result.setTotal(comments.size());

        return result;
    }

    // 无限级递归
    private List<Comment> buildCommentTree(List<Comment> list, Integer cid) {
        List<Comment> children = new ArrayList<>();
        for (Comment comment : list) {
            if (comment.getCommentId().equals(cid)) {
                comment.setChildren(buildCommentTree(list, comment.getId()));
                children.add(comment);
            }

            // 文章标题
            Article article = articleMapper.selectById(comment.getArticleId());
            comment.setArticleTitle(article.getTitle());
        }
        return children;
    }
}