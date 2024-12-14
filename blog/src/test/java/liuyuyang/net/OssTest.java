package liuyuyang.net;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import liuyuyang.net.model.Oss;
import liuyuyang.net.service.OssService;
import liuyuyang.net.vo.PageVo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author laifeng
 * @version 1.0
 * @date 2024/12/11 9:47
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OssTest {
    @Resource
    private OssService ossService;
    @Resource
    private FileStorageService fileStorageService;//

    @Test
    public void test1() {
        PageVo pageVo = new PageVo();
        pageVo.setPage(1);
        pageVo.setSize(10);
        Page<Oss> ossPage = ossService.ossPage(null, pageVo);
        System.out.println(ossPage.getRecords());
    }

    @Test
    public void test2() {

    }
}
