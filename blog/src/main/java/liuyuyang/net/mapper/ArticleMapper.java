package liuyuyang.net.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import liuyuyang.net.model.Article;
import liuyuyang.net.model.Cate;
import liuyuyang.net.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    // 通过文章ID查询绑定的所有分类
    @Select("select c.* from article a, cate c where a.id = #{id} && FIND_IN_SET(c.id, a.cate_ids)")
    public List<Cate> getCateList(Integer id);

    // 通过文章ID查询绑定的所有标签
    @Select("select t.* from article a, tag t where a.id = #{id} && FIND_IN_SET(t.id, a.tag_ids)")
    public List<Tag> getTagList(Integer id);
}
