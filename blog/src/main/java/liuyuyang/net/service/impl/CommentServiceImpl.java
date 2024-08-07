package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.CommentMapper;
import liuyuyang.net.model.Comment;
import liuyuyang.net.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Resource
    private CommentMapper CommentMapper;

    @Override
    public Page<Comment> list(Integer page, Integer size) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Comment> result = new Page<>(page, size);
        CommentMapper.selectPage(result, queryWrapper);

        return result;
    }
}