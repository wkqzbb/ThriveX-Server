package liuyuyang.net.controller;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RssController {
    public static void main(String[] args) {
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
                for (SyndEntry entry : feed.getEntries()) {
                    System.out.println("Title: " + entry.getTitle());
                    System.out.println("Link: " + entry.getLink());
                    System.out.println("Published Date: " + entry.getPublishedDate());
                    System.out.println("Description: " + entry.getDescription().getValue());
                    System.out.println();
                }
            } catch (Exception e) {
                System.err.println("Error parsing feed: " + feedUrl);
                e.printStackTrace();
            }
        }
    }
}
