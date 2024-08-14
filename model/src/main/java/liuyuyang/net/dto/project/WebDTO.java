package liuyuyang.net.dto.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WebDTO {
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
}
