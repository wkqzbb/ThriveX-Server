package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.mapper.OssMapper;
import liuyuyang.net.model.Oss;
import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.OssUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OssServiceImpl extends ServiceImpl<OssMapper, Oss> implements OssService {
    @Resource
    private OssMapper ossMapper;

    @Override
    public List<Oss> list() {
        QueryWrapper<Oss> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(Oss::getId);
        List<Oss> list = ossMapper.selectList(queryWrapper);

        for (Oss data : list) {
            data.setAccessKey(maskMiddleTen(data.getAccessKey()));
            data.setSecretKey(maskMiddleTen(data.getSecretKey()));
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enable(Integer id) {
        // 先禁用所有的配置
        boolean temp1 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getIsEnable, 0));
        if (!temp1) {
            throw new CustomException("操作失败");
        }

        // 再启用制定的配置
        boolean temp2 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getIsEnable, 1).eq(Oss::getId, id));
        if (!temp2) {
            throw new CustomException("操作失败");
        }

        Oss oss = this.getById(id);
        OssUtil.registerPlatform(oss);
    }

    public Oss getEnableOss() {
        QueryWrapper<Oss> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Oss::getIsEnable, 1);
        return ossMapper.selectOne(queryWrapper);
    }

    @Override
    public void disable(Integer id) {
        boolean temp1 = this.update(Wrappers.<Oss>update().lambda().set(Oss::getIsEnable, 0));
        if (!temp1) {
            throw new CustomException("更新失败");
        }
        OssUtil.removeStorage(OssUtil.getStorageList(), OssUtil.getPlatform());
        OssUtil.setPlatformToDefault();
    }

    @Override
    public void delOss(Integer id) {
        Oss oss = this.getById(id);
        if (oss == null) {
            throw new CustomException("删除失败");
        }
        boolean result = this.removeById(id);
        if (result) {
            OssUtil.removeStorage(OssUtil.getStorageList(), oss.getPlatform());
        }
    }

    @Override
    public void saveOss(Oss oss) {
        // 判断是否有重复
        Integer count = this.lambdaQuery()
                .eq(Oss::getPlatform, oss.getPlatform())
                .count();
        if (count > 0) {
            throw new CustomException("平台已存在");
        }
        this.save(oss);
    }

    public static String maskMiddleTen(String input) {
        if (input.length() <= 10) {
            return input; // 如果长度小于或等于10，不做处理
        }

        int start = (input.length() - 10) / 2;
        int end = start + 10;

        StringBuilder masked = new StringBuilder(input);
        for (int i = start; i < end; i++) {
            masked.setCharAt(i, '*');
        }

        return masked.toString();
    }
}
