package liuyuyang.net.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import liuyuyang.net.dto.project.SystemDTO;
import liuyuyang.net.mapper.ProjectMapper;
import liuyuyang.net.model.Project;
import liuyuyang.net.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;


@Service
@Transactional
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Override
    public SystemDTO getSystemInfo() {
        SystemInfo systemInfo = new SystemInfo();

        // 获取操作系统信息
        OperatingSystem os = systemInfo.getOperatingSystem();
        // 获取系统名称
        String osName = os.getFamily();
        // 获取系统版本
        String osVersion = os.getVersionInfo().getVersion();

        // 获取操作系统内存信息
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        // 总内存量
        long totalMemory = memory.getTotal();
        // 可用内存量
        long availableMemory = memory.getAvailable();
        // 内存使用率
        double memoryUsage = (double) (totalMemory - availableMemory) / totalMemory * 100;

        SystemDTO data = new SystemDTO();
        data.setOsName(osName);
        data.setOsVersion(osVersion);
        data.setTotalMemory((int) (totalMemory / (1024.0 * 1024 * 1024)));
        data.setAvailableMemory((int) (availableMemory / (1024.0 * 1024 * 1024)));
        data.setMemoryUsage(Float.valueOf(String.format("%.2f", memoryUsage)));

        return data;
    }

}