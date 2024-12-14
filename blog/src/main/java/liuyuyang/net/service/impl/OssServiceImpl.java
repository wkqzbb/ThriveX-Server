package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.OssMapper;
import liuyuyang.net.model.Oss;
import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.OssUtil;
import liuyuyang.net.vo.PageVo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class OssServiceImpl extends ServiceImpl<OssMapper, Oss> implements OssService {
    @Resource
    @Lazy
    private OssUtil ossUtil;

    @Override
    public Page<Oss> ossPage(Oss oss, PageVo pageVo) {
        QueryWrapper<Oss> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(Oss::getId);
        Page<Oss> page = new Page<>(pageVo.getPage(), pageVo.getSize());
        this.page(page, queryWrapper);
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Integer id) {
        // 先禁用
        boolean temp1 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getIsEnable, 0));
        if (!temp1) {
            throw new RuntimeException("更新失败");
        }
        // 再启用
        boolean temp2 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getIsEnable, 1).eq(Oss::getId, id));
        if (!temp2) {
            throw new RuntimeException("更新失败");
        }
        Oss oss = this.getById(id);
        ossUtil.registerPlatform(oss);
    }

    public Oss getEnableOss() {
        QueryWrapper<Oss> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Oss::getIsEnable, 1);
        return this.getOne(queryWrapper);
    }
}
