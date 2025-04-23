package liuyuyang.net.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.model.Album;

import java.util.List;

public interface AlbumService extends IService<Album> {
    // 创建相册
    void add(Album album);
    
    // 删除相册
    void del(Integer id);
    
    // 批量删除相册
    void batchDel(List<Integer> ids);
    
    // 更新相册
    void edit(Album album);
    
    // 获取单个相册
    Album get(Integer id);
    
    // 获取相册列表
    List<Album> list();
    
    // 分页查询相册列表
    Page<Album> paging(Integer page, Integer size);
}
