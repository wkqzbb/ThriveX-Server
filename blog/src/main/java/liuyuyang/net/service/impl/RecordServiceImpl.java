package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.*;
import liuyuyang.net.model.*;
import liuyuyang.net.service.RecordService;
import liuyuyang.net.utils.YuYangUtils;
import liuyuyang.net.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private YuYangUtils yuYangUtils;

    @Override
    public Page<Record> paging(PageVo pageVo) {
        List<Record> list = recordMapper.selectList(null);
        return yuYangUtils.getPageData(pageVo, list);
    }
}