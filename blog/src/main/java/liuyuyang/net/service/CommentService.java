package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Comment;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;

import java.util.List;

public interface CommentService extends IService<Comment> {
    public Comment get(Integer id);

    public Page<Comment> getCommentList(Integer aid, PageVo pageVo);

    public List<Comment> list(String pattern);

    public Page<Comment> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo);
}
