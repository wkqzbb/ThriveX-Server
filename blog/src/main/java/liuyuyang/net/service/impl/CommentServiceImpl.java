package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Comment;
import liuyuyang.net.model.Comment;
import liuyuyang.net.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Override
    public Comment get(Integer id) {
        Comment data = commentMapper.selectById(id);

        if (data == null) {
            throw new YuYangException(400, "该评论不存在");
        }

        // 获取当前评论下的所有子评论
        List<Comment> comments = commentMapper.selectList(null);
        data.setChildren(buildCommentTree(comments, id));

        return data;
    }

    @Override
    public List<Comment> getCommentList(Integer aid) {
        // 查询所有评论
        List<Comment> data = commentMapper.getCommentList(aid);
        // 构建评论树
        List<Comment> result = buildCommentTree(data, 0);
        return result;
    }


    @Override
    public List<Comment> list() {
        // 查询所有评论
        List<Comment> data = commentMapper.selectList(null);
        // 构建评论树
        List<Comment> result = buildCommentTree(data, 0);
        return result;
    }

    @Override
    public Page<Comment> paging(Integer page, Integer size) {
        // 查询所有评论
        List<Comment> data = commentMapper.selectList(new QueryWrapper<>());
        // 构建评论树
        List<Comment> comments = buildCommentTree(data, 0);

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
        }
        return children;
    }
}