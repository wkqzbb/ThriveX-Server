package liuyuyang.net.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;

import java.util.List;

public class YuYangUtils {
    // 分页查询逻辑
    public static <T> Page<T> getPageData(PageVo pageVo, List<T> list) {
        int start = (pageVo.getPage() - 1) * pageVo.getSize();
        int end = Math.min(start + pageVo.getSize(), list.size());
        List<T> pagedRecords = list.subList(start, end);

        Page<T> result = new Page<>(pageVo.getPage(), pageVo.getSize());
        result.setRecords(pagedRecords);
        result.setTotal(list.size());

        return result;
    }

    // 过滤数据
    public static <T> QueryWrapper<T> queryWrapperFilter(FilterVo filterVo, SortVO sortVo) {
        return queryWrapperFilter(filterVo, sortVo, "title");
    }

    public static <T> QueryWrapper<T> queryWrapperFilter(FilterVo filterVo, SortVO sortVo, String key) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        // 根据时间从早到晚排序
        switch (sortVo.getSort()) {
            case "asc":
                queryWrapper.orderByAsc("create_time");
                break;
            case "desc":
                queryWrapper.orderByDesc("create_time");
                break;
        }

        // 根据关键字通过标题过滤出对应数据
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like(key, "%" + filterVo.getKey() + "%");
        }

        // 根据开始与结束时间过滤
        if (filterVo.getStartDate() != null && filterVo.getEndDate() != null) {
            queryWrapper.between("create_time", filterVo.getStartDate(), filterVo.getEndDate());
        } else if (filterVo.getStartDate() != null) {
            queryWrapper.ge("create_time", filterVo.getStartDate());
        } else if (filterVo.getEndDate() != null) {
            queryWrapper.le("create_time", filterVo.getEndDate());
        }

        return queryWrapper;
    }

}
