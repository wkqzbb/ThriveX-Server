package liuyuyang.net.listener;

import liuyuyang.net.model.Oss;
import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.OssUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 将启用的 OSS 配置注册到存储平台
 */
@Component
@AllArgsConstructor
public class OssStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final OssService ossService;
    private final OssUtil ossUtil;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 确保是 Spring 应用上下文初始化完成的事件
        if (event.getApplicationContext().getParent() == null) {
            // 查询启用的 OSS 配置
            Oss enabledOss = ossService.getEnableOss();
            if (enabledOss != null) {
                // 注册到存储平台
                registerOssToPlatform(enabledOss);
            } else {
                System.out.println("No enabled OSS configuration found.");
            }
        }
    }

    private void registerOssToPlatform(Oss oss) {
        ossUtil.registerPlatform(oss);
    }
}