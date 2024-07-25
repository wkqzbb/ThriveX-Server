package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.LinkMapper;
import liuyuyang.net.model.Link;
import liuyuyang.net.service.LinkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Resource
    private LinkMapper LinkMapper;

    @Override
    public Page<Link> list(Integer page, Integer size) {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Link> result = new Page<>(page, size);
        LinkMapper.selectPage(result, queryWrapper);

        return result;
    }
}