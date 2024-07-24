package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.GuiguException;
import liuyuyang.net.mapper.CateMapper;
import liuyuyang.net.model.Cate;
import liuyuyang.net.service.CateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class CateServiceImpl extends ServiceImpl<CateMapper, Cate> implements CateService {
    @Resource
    private CateMapper cateMapper;

    @Override
    public Boolean exist(Integer cid) {
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("level", cid);

        List<Cate> data = cateMapper.selectList(queryWrapper);
        System.out.println(data);

        // 删除之前先判断该数据中是否绑定了其他数据
        if (!data.isEmpty()) {
            throw new GuiguException(400, "ID为：" + cid + "的分类中绑定了 " + data.size() + " 个二级分类，请解绑后重试");
        }

        return true;
    }

    @Override
    public Page<Cate> list(Integer page, Integer size) {
        QueryWrapper<Cate> queryWrapper = new QueryWrapper<>();

        // 分页查询
        Page<Cate> result = new Page<>(page, size);
        cateMapper.selectPage(result, queryWrapper);

        return result;
    }
}