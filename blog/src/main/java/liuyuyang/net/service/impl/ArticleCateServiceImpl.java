package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.mapper.ArticleCateMapper;
import liuyuyang.net.model.ArticleCate;
import liuyuyang.net.service.ArticleCateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 文章和分类的中间表服务实现类
 *
 * @author laifeng
 * @version 1.0
 * @date 2024/11/15 20:34
 */
@Service
@AllArgsConstructor
public class ArticleCateServiceImpl extends ServiceImpl<ArticleCateMapper, ArticleCate> implements ArticleCateService {
}
