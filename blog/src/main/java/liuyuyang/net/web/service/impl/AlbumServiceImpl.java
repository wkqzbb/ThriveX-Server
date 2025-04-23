package liuyuyang.net.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.common.execption.CustomException;
import liuyuyang.net.model.Album;
import liuyuyang.net.web.mapper.AlbumMapper;
import liuyuyang.net.web.service.AlbumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {
    @Resource
    private AlbumMapper albumMapper;

    @Override
    public void add(Album album) {
        System.out.println(album);
        save(album);
    }

    @Override
    public void del(Integer id) {
        Album album = albumMapper.selectById(id);
        if (album == null) {
            throw new CustomException(400, "该相册不存在");
        }
        removeById(id);
    }

    @Override
    public void batchDel(List<Integer> ids) {
        for (Integer id : ids) {
            del(id);
        }
    }

    @Override
    public void edit(Album album) {
        Album existAlbum = albumMapper.selectById(album.getId());
        if (existAlbum == null) {
            throw new CustomException(400, "该相册不存在");
        }
        updateById(album);
    }

    @Override
    public Album get(Integer id) {
        Album album = albumMapper.selectById(id);
        if (album == null) {
            throw new CustomException(400, "该相册不存在");
        }
        return album;
    }

    @Override
    public List<Album> list() {
        LambdaQueryWrapper<Album> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Album::getId);
        return list(lambdaQueryWrapper);
    }

    @Override
    public Page<Album> paging(Integer page, Integer size) {
        LambdaQueryWrapper<Album> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Album::getId);
        return page(new Page<>(page, size), lambdaQueryWrapper);
    }
}