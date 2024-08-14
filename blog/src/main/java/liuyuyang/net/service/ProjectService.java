package liuyuyang.net.service;

import com.baomidou.mybatisplus.extension.service.IService;
import liuyuyang.net.dto.project.SystemDTO;
import liuyuyang.net.model.Project;

public interface ProjectService extends IService<Project> {
    public SystemDTO getSystemInfo();
}
