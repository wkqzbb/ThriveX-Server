package liuyuyang.net.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.spring.web.json.Json;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "封面图片列表", example = "[\"https://blog.liuyuyang.net/1.jpg\", \"https://blog.liuyuyang.net/2.jpg\"]")
    private String covers;

    @ApiModelProperty(value = "暗色模式Logo", example = "https://blog.liuyuyang.net/dark_logo.png")
    private String darkLogo;

    @ApiModelProperty(value = "网站描述", example = "记录前端、Python、Java点点滴滴")
    private String description;

    @ApiModelProperty(value = "网站图标", example = "https://blog.liuyuyang.net/favicon.ico")
    private String favicon;

    @ApiModelProperty(value = "网站字体", example = "https://blog.liuyuyang.net/LXGWWenKai.ttf")
    private String font;

    @ApiModelProperty(value = "网站底部信息", example = "一直对网站开发领域很感兴趣，从小就希望有一个属于自己的网站，在17年时候成功进入站长圈，并通过各种自学，以及各种折腾，才有了你现在看到的这个网站")
    private String footer;

    @ApiModelProperty(value = "关键词", example = "Thrive,前端,Python,Java", required = true)
    private String keyword;

    @ApiModelProperty(value = "亮色模式Logo", example = "https://blog.liuyuyang.net/light_logo.png")
    private String lightLogo;

    @ApiModelProperty(value = "社交链接列表", example = "[{\"name\": \"GitHub\", \"url\": \"https://github.com/LiuYuYang01\"}, {\"name\": \"Gitee\", \"url\": \"https://gitee.com/liu_yu_yang666\"}]")
    private String social;

    @ApiModelProperty(value = "副标题", example = "花有重开日, 人无再少年")
    private String subhead;

    @ApiModelProperty(value = "网站标题", example = "Thrive", required = true)
    private String title;

    @ApiModelProperty(value = "网站URL", example = "https://blog.liuyuyang.net/", required = true)
    private String url;

    @ApiModelProperty(value = "文章列表布局", example = "classics")
    private String isArticleLayout;

    @ApiModelProperty(value = "右侧功能栏布局", example = "[\"author\", \"hotArticle\", \"randomArticle\", \"newComments\"]")
    private String rightSidebar;

    @ApiModelProperty(value = "首页背景图", example = "1.jpg")
    private String swiperImage;

    @ApiModelProperty(value = "打字机文本", example = "[\"这是第一段文本\", \"这是第二段文本\", \"...\"]")
    private String swiperText;

    @ApiModelProperty(value = "作者推荐文章", example = "1,2,3")
    private String recoArticle;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作系统名称", example = "Windows 10")
    private String osName;

    @TableField(exist = false)
    @ApiModelProperty(value = "操作系统版本", example = "10.0.19042")
    private String osVersion;

    @TableField(exist = false)
    @ApiModelProperty(value = "总内存量 (MB)", example = "16384")
    private Integer totalMemory;

    @TableField(exist = false)
    @ApiModelProperty(value = "可用内存量 (MB)", example = "8192")
    private Integer availableMemory;

    @TableField(exist = false)
    @ApiModelProperty(value = "内存使用率 (%)", example = "50.0")
    private Float memoryUsage;
}