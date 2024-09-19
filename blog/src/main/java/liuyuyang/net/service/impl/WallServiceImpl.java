package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.WallMapper;
import liuyuyang.net.model.Wall;
import liuyuyang.net.service.WallService;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static liuyuyang.net.utils.YuYangUtils.getPageData;
import static liuyuyang.net.utils.YuYangUtils.queryWrapperFilter;

@Service
@Transactional
public class WallServiceImpl extends ServiceImpl<WallMapper, Wall> implements WallService {
    @Resource
    private WallMapper wallMapper;

    @Override
    public Wall get(Integer id) {
        Wall data = wallMapper.selectById(id);

        if (data == null) {
            throw new CustomException(400, "该留言不存在");
        }

        return data;
    }

    @Override
    public List<Wall> list(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Wall> queryWrapper = queryWrapperFilter(filterVo, sortVo);
        List<Wall> list = wallMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public Page<Wall> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        List<Wall> list = list(filterVo, sortVo);
        return getPageData(pageVo, list);
    }

    @Override
    public Page<Wall> getWallList(Integer aid, PageVo pageVo) {
        QueryWrapper<Wall> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");

        Page<Wall> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        wallMapper.selectPage(page, queryWrapper);

        List<Wall> list = page.getRecords();

        // 分页处理
        return getPageData(pageVo, list);
    }
}
