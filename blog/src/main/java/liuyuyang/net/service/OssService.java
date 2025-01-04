package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Oss;
import liuyuyang.net.vo.PageVo;

import java.util.List;
import java.util.Map;

public interface OssService extends IService<Oss> {
    /**
     * 分页
     */
    List<Oss> list();

    void enable(Integer id);

    /**
     * 获取当前启用的oss
     */
    Oss getEnableOss();

    void disable(Integer id);

    /**
     * 删除
     */
    void delOss(Integer id);

    /**
     * 新增
     * @param oss
     */
    void saveOss(Oss oss);

    // 获取支持的平台
    List<Map> getPlatform();
}
