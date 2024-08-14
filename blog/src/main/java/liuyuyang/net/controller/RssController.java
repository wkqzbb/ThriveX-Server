package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.model.Rss;
import liuyuyang.net.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.*;

@Api(tags = "订阅管理")
@RestController
@RequestMapping("/rss")
public class RssController {
    @GetMapping
    @ApiOperation("获取订阅内容")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<List<Rss>> list() {
        List<Rss> rssList = new ArrayList<>();

        // 目标网站列表
        List<String> feedUrls = Arrays.asList(
                "https://blog.zwying.com/feed/",
                "https://blog.liuyuyang.net/index.php/feed/"
        );

        for (String feedUrl : feedUrls) {
            try {
                // 创建一个URL对象
                URL url = new URL(feedUrl);
                // 为URL创建一个XmlReader
                XmlReader xmlReader = new XmlReader(url);
                // 创建一个SyndFeedInput对象
                SyndFeedInput input = new SyndFeedInput();
                // 从XmlReader构建SyndFeed对象
                SyndFeed feed = input.build(xmlReader);

                // 遍历提要中的条目
                for (SyndEntry data : feed.getEntries()) {
                    Rss rss = new Rss();
                    rss.setAuthor(data.getAuthor());
                    rss.setTitle(data.getTitle());
                    rss.setDescription(data.getDescription().getValue());
                    rss.setUrl(data.getLink());
                    rss.setCreateTime(String.valueOf(data.getPublishedDate().getTime()));
                    rssList.add(rss);
                }
            } catch (Exception e) {
                System.err.println("解析失败: " + feedUrl);
                e.printStackTrace();
            }
        }

        // 对rssList进行排序，根据createTime降序排序
        Collections.sort(rssList, new Comparator<Rss>() {
            @Override
            public int compare(Rss o1, Rss o2) {
                long time1 = Long.parseLong(o1.getCreateTime());
                long time2 = Long.parseLong(o2.getCreateTime());
                // 降序排序
                return Long.compare(time2, time1);
            }
        });

        return Result.success(rssList);
    }
}
