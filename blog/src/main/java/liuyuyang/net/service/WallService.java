package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Wall;
import liuyuyang.net.model.WallCate;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.wall.WallFilterVo;

import java.util.List;

public interface WallService extends IService<Wall> {
    public void add(Wall wall) throws Exception;
    public Wall get(Integer id);

    public Page<Wall> getCateWallList(Integer cateId, PageVo pageVo);
    public List<WallCate> getCateList();

    public List<Wall> list(WallFilterVo filterVo);

    public Page<Wall> paging(WallFilterVo filterVo, PageVo pageVo);
}
