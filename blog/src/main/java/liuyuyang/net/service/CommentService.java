package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Comment;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.comment.CommentFilterVo;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public void add(Comment comment) throws Exception;
    public Comment get(Integer id);
    public Page<Comment> getArticleCommentList(Integer articleId, PageVo pageVo);
    public List<Comment> list(CommentFilterVo filterVo, SortVO sortVo);
    public Page<Comment> paging(CommentFilterVo filterVo, SortVO sortVo, PageVo pageVo);
}
