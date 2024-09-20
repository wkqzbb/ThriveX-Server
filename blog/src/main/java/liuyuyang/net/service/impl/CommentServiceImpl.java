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
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.comment.CommentFilterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static liuyuyang.net.utils.YuYangUtils.*;

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

        // 获取当前评论下的所有子评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id", id);
        List<Comment> list = commentMapper.selectList(queryWrapper);
        data.setChildren(buildCommentTree(list, id));

        return data;
    }

    @Override
    public List<Comment> list(CommentFilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Comment> queryWrapper = queryWrapperFilter(filterVo, sortVo);
        List<Comment> list = commentMapper.selectList(queryWrapper);

        // 查询的结构格式
        if (Objects.equals(filterVo.getPattern(), "list")) return list;

        // 构建多级评论
        return buildCommentTree(list, 0);
    }

    @Override
    public Page<Comment> paging(CommentFilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        List<Comment> list = list(filterVo, sortVo);
        return getPageData(pageVo, list);
    }

    @Override
    public Page<Comment> getArticleCommentList(Integer article_id, PageVo pageVo) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", article_id);
        queryWrapper.eq("audit_status", 1);
        queryWrapper.orderByDesc("create_time");

        Page<Comment> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        commentMapper.selectPage(page, queryWrapper);

        List<Comment> list = page.getRecords();

        // 构建评论树
        list = buildCommentTree(list, 0);

        // 分页处理
        return getPageData(pageVo, list);
    }

    // 递归构建评论列表
    private List<Comment> buildCommentTree(List<Comment> list, Integer cid) {
        List<Comment> children = new ArrayList<>();

        for (Comment data : list) {
            if (data.getCommentId().equals(cid)) {
                data.setChildren(buildCommentTree(list, data.getId()));
                children.add(data);
            }

            // 绑定对应的数据
            Article article = articleMapper.selectById(data.getArticleId());
            if (article != null) {
                data.setArticleTitle(article.getTitle());
            }
        }
        return children;
    }
}
