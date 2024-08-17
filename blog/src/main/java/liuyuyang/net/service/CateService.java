package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Cate;

import java.util.List;

public interface CateService extends IService<Cate> {
    public Boolean exist(Integer cid);
    public Cate get(Integer cid);
    public List<Cate> list(String pattern);
    public Page<Cate> paging(Integer page, Integer size);
}
