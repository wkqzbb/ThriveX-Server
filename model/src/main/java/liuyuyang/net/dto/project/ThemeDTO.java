package liuyuyang.net.dto.project;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThemeDTO {
    @ApiModelProperty(value = "文章列表布局", example = "classics")
    private String isArticleLayout;

    @ApiModelProperty(value = "右侧功能栏布局", example = "[\"author\", \"hotArticle\", \"randomArticle\", \"newComments\"]")
    private String rightSidebar;

    @ApiModelProperty(value = "亮色模式Logo", example = "https://blog.liuyuyang.net/light_logo.png")
    private String lightLogo;

    @ApiModelProperty(value = "暗色模式Logo", example = "https://blog.liuyuyang.net/dark_logo.png")
    private String darkLogo;

    @ApiModelProperty(value = "首页背景图", example = "1.jpg")
    private String swiperImage;

    @ApiModelProperty(value = "封面图片列表", example = "[\"https://blog.liuyuyang.net/1.jpg\", \"https://blog.liuyuyang.net/2.jpg\"]")
    private String covers;

    @ApiModelProperty(value = "社交链接列表", example = "[{\"name\": \"GitHub\", \"url\": \"https://github.com/LiuYuYang01\"}, {\"name\": \"Gitee\", \"url\": \"https://gitee.com/liu_yu_yang666\"}]")
    private String social;

    @ApiModelProperty(value = "打字机文本", example = "[\"这是第一段文本\", \"这是第二段文本\", \"...\"]")
    private String swiperText;
}
