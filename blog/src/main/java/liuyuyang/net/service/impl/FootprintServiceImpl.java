package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.*;
import liuyuyang.net.model.Footprint;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.FootprintService;
import liuyuyang.net.vo.FilterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, Footprint> implements FootprintService {
    @Resource
    private FootprintMapper footprintMapper;

    @Override
    public List<Footprint> list(FilterVo filterVo) {
        QueryWrapper<Footprint> queryWrapper = new QueryWrapper<>();

        // 根据关键字通过标题过滤出对应文章数据
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like("address", "%" + filterVo.getKey() + "%");
        }

        // 根据开始与结束时间过滤
        if (filterVo.getStartDate() != null && filterVo.getEndDate() != null) {
            queryWrapper.between("time", filterVo.getStartDate(), filterVo.getEndDate());
        } else if (filterVo.getStartDate() != null) {
            queryWrapper.ge("time", filterVo.getStartDate());
        } else if (filterVo.getEndDate() != null) {
            queryWrapper.le("time", filterVo.getEndDate());
        }

        List<Footprint> list = footprintMapper.selectList(queryWrapper);
        System.out.println(list);
        System.out.println(5555);
        return list;
    }
}