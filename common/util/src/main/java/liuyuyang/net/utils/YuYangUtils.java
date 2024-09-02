package liuyuyang.net.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import liuyuyang.net.vo.PageVo;

import java.util.List;

public class YuYangUtils {
    // 分页查询逻辑
    public static Page getPageObject(PageVo pageVo, List list) {
        int start = (pageVo.getPage() - 1) * pageVo.getSize();
        int end = Math.min(start + pageVo.getSize(), list.size());
        List pagedComments = list.subList(start, end);

        Page result = new Page<>(pageVo.getPage(), pageVo.getSize());
        result.setRecords(pagedComments);
        result.setTotal(list.size());

        return result;
    }
}
