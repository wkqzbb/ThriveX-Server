package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Record;
import liuyuyang.net.vo.PageVo;

public interface RecordService extends IService<Record> {
    Page<Record> paging(PageVo pageVo);
}
