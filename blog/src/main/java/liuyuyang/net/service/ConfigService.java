package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.dto.project.SystemDTO;
import liuyuyang.net.model.Config;

import java.util.Map;

public interface ConfigService extends IService<Config> {
    Map list(String group);
    void edit(String group, Map<String, String> config);
    SystemDTO getSystemInfo();
}
