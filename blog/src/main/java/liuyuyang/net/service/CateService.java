package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Cate;

public interface CateService extends IService<Cate> {
    public Boolean exist(Integer cid);
    public Page<Cate> list(Integer page, Integer size);
}
