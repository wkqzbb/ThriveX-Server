package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.Claims;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.LinkMapper;
import liuyuyang.net.mapper.LinkTypeMapper;
import liuyuyang.net.model.Link;
import liuyuyang.net.properties.JwtProperties;
import liuyuyang.net.service.LinkService;
import liuyuyang.net.utils.JwtUtil;
import liuyuyang.net.vo.FilterVo;
import liuyuyang.net.vo.PageVo;
import liuyuyang.net.vo.SortVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static liuyuyang.net.utils.YuYangUtils.queryWrapperFilter;

@Service
@Transactional
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Autowired
    private JwtProperties jwtProperties;
    @Resource
    private LinkMapper linkMapper;
    @Resource
    private LinkTypeMapper linkTypeMapper;

    @Override
    public void add(Link link, String token) {
        if (token == null) {
            // 添加之前先判断所选的网站类型是否为当前用户可选的
            Integer isAdmin = linkTypeMapper.selectById(link.getTypeId()).getIsAdmin();
            if (isAdmin == 1) throw new CustomException(400, "该类型需要管理员权限才能添加");
            linkMapper.insert(link);
        } else {
            if (token.startsWith("Bearer ")) token = token.substring(7);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            Map<String, Object> user = (Map<String, Object>) claims.get("user");

            // 是管理员，允许添加
            if ("1".equals(user.get("roleId"))) {
                linkMapper.insert(link);
            } else {
                throw new CustomException(400, "该类型需要管理员权限才能添加");
            }
        }
    }

    @Override
    public Link get(Integer id) {
        Link data = linkMapper.selectById(id);

        if (data == null) {
            throw new CustomException(400, "该网站不存在");
        }

        // 获取网站类型
        data.setType(linkTypeMapper.selectById(id));

        return data;
    }

    @Override
    public List<Link> list(FilterVo filterVo, SortVO sortVo) {
        QueryWrapper<Link> queryWrapper = queryWrapperFilter(filterVo, sortVo);
        // queryWrapper.eq("audit_status", 1); // 只显示审核成功的网站

        // 查询所有网站
        List<Link> list = linkMapper.selectList(queryWrapper);

        if (!list.isEmpty()) {
            for (Link link : list) {
                link.setType(linkTypeMapper.selectById(link.getTypeId()));
            }
        }

        list = list.stream().sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime())).collect(Collectors.toList());

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
}