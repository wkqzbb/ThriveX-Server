package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.mapper.LinkMapper;
import liuyuyang.net.mapper.LinkTypeMapper;
import liuyuyang.net.model.Link;
import liuyuyang.net.service.LinkService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Resource
    private LinkMapper linkMapper;
    @Resource
    private LinkTypeMapper linkTypeMapper;

    @Override
    public Link get(Integer id) {
        Link data = linkMapper.selectById(id);

        if (data == null) {
            throw new YuYangException(400, "该网站不存在");
        }

        // 获取网站类型
        data.setType(linkTypeMapper.getTypeData(id));

        return data;
    }


    @Override
    public List<Link> list() {
        // 查询所有网站
        List<Link> list = linkMapper.selectList(new QueryWrapper<>());

        for (Link link : list) {
            link.setType(linkTypeMapper.getTypeData(link.getId()));
        }

        return list;
    }

    @Override
    public Page<Link> paging(Integer page, Integer size) {
        // 查询所有网站
        List<Link> list = linkMapper.selectList(null);

        for (Link link : list) {
            link.setType(linkTypeMapper.getTypeData(link.getId()));
        }

        // 分页处理
        int start = (page - 1) * size;
        int end = Math.min(start + size, list.size());
        List<Link> pagedLinks = list.subList(start, end);

        // 返回分页结果
        Page<Link> result = new Page<>(page, size);
        result.setRecords(pagedLinks);
        result.setTotal(list.size());

        return result;
    }
}