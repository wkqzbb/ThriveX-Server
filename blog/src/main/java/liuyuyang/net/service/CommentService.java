package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Comment;

public interface CommentService extends IService<Comment> {
    public Page<Comment> list(Integer page, Integer size);
}
