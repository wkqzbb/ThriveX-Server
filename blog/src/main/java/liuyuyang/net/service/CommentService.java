package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public Comment get(Integer id);
    public List<Comment> getCommentList(Integer aid);
    public List<Comment> list();
    public Page<Comment> paging(Integer page, Integer size);
}
