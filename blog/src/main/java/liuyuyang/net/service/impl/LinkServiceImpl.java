package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.LinkMapper;
import liuyuyang.net.mapper.LinkTypeMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.ArticleCate;
import liuyuyang.net.model.Link;
import liuyuyang.net.service.LinkService;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import liuyuyang.net.vo.article.ArticleFillterVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
            throw new CustomException(400, "该网站不存在");
        }

        // 获取网站类型
        data.setType(linkTypeMapper.getTypeData(id));

        return data;
    }


    @Override
    public List<Link> list(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Link> queryWrapper = queryWrapperArticle(filterVo, sortVo);

        // 查询所有网站
        List<Link> list = linkMapper.selectList(queryWrapper);

        if (!list.isEmpty()) {
            for (Link link : list) {
                link.setType(linkTypeMapper.getTypeData(link.getId()));
            }
        }

        return list;
    }

    @Override
    public Page<Link> paging(FilterVo filterVo, SortVO sortVo, PageVo pageVo) {
        List<Link> list = list(filterVo, sortVo);

        // 分页处理
        int start = (pageVo.getPage() - 1) * pageVo.getSize();
        int end = Math.min(start + pageVo.getSize(), list.size());
        List<Link> pagedLinks = list.subList(start, end);

        // 返回分页结果
        Page<Link> result = new Page<>(pageVo.getPage(), pageVo.getSize());
        result.setRecords(pagedLinks);
        result.setTotal(list.size());

        return result;
    }

    // 过滤数据
    @Override
    public QueryWrapper<Link> queryWrapperArticle(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Link> queryWrapper = new QueryWrapper<>();

        // 根据发布时间从早到晚排序
        switch (sortVo.getSort()) {
            case "asc":
                queryWrapper.orderByAsc("create_time");
                break;
            case "desc":
                queryWrapper.orderByDesc("create_time");
                break;
        }

        // 根据关键字通过标题过滤出对应文章数据
        if (filterVo.getKey() != null && !filterVo.getKey().isEmpty()) {
            queryWrapper.like("title", "%" + filterVo.getKey() + "%");
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