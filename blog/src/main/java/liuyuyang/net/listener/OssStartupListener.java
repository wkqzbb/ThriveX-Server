package liuyuyang.net.listener;

import liuyuyang.net.model.Oss;
import liuyuyang.net.service.OssService;
import liuyuyang.net.utils.OssUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileStorageProperties;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将启用的 OSS 配置注册到存储平台
 */
@Component
@AllArgsConstructor
@Slf4j
public class OssStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private final OssService ossService;
    private final FileStorageService fileStorageService;

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
                System.out.println("没有发现启用的OSS配置。");
            }
        }
        FileStorageProperties properties = fileStorageService.getProperties();
        List<? extends FileStorageProperties.LocalPlusConfig> localPlus = properties.getLocalPlus();
        if (localPlus != null && !localPlus.isEmpty()) {
            for (FileStorageProperties.LocalPlusConfig config : localPlus) {
                if (OssUtil.DEFAULT_PLATFORM.equals(config.getPlatform())) {
                    // 将默认平台配置的访问路径设置为项目根路径
                    String projectDir = System.getProperty("user.dir");
                    // 判断是win还是linux,来设置分隔符
                    String separator = System.getProperty("os.name").toLowerCase().contains("win") ? "\\" : "/";
                    projectDir += separator;
                    config.setStoragePath(projectDir);
                    log.info("将默认存储平台的存储路径设置为：" + projectDir);
                }
            }
        }

    }

    private void registerOssToPlatform(Oss oss) {
        OssUtil.registerPlatform(oss);
    }
}