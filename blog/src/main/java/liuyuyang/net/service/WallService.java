package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Wall;
import liuyuyang.net.model.WallCate;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;

import java.util.List;

public interface WallService extends IService<Wall> {
    public Wall get(Integer id);

    public Page<Wall> getCateWallList(Integer cate_id, PageVo pageVo);
    public List<WallCate> getCateList();

    public List<Wall> list(FilterVo filterVo, SortVO sortVo);

    public Page<Wall> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo);
}
