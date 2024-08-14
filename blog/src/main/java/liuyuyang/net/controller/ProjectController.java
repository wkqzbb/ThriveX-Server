package liuyuyang.net.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import liuyuyang.net.dto.project.LayoutDTO;
import liuyuyang.net.dto.project.SystemDTO;
import liuyuyang.net.dto.project.WebDTO;
import liuyuyang.net.mapper.ProjectMapper;
import liuyuyang.net.model.Project;
import liuyuyang.net.result.Result;
import liuyuyang.net.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "项目管理")
@RestController
@RequestMapping("/project")
@Transactional
public class ProjectController {
    @Resource
    private ProjectMapper projectMapper;
    @Resource
    private ProjectService projectService;

    @PatchMapping("/web")
    @ApiOperation("修改网站")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 1)
    public Result<String> editWeb(@RequestBody WebDTO data) {
        Project project = new Project();
        BeanUtils.copyProperties(data, project);
        project.setId(1);
        projectMapper.updateById(project);
        return Result.success();
    }

    @GetMapping("/web")
    @ApiOperation("获取网站配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 2)
    public Result getWeb() {
        Project config = projectMapper.selectById(1);

        WebDTO data = new WebDTO();

        // data.setCovers(config.getCovers());
        // data.setDarkLogo(config.getDarkLogo());
        // data.setDescription(config.getDescription());
        // data.setFavicon(config.getFavicon());
        // data.setFont(config.getFont());
        // data.setFooter(config.getFooter());
        // data.setKeyword(config.getKeyword());
        // data.setLightLogo(config.getLightLogo());
        // data.setSocial(config.getSocial());
        // data.setSubhead(config.getSubhead());
        // data.setTitle(config.getTitle());
        // data.setUrl(config.getUrl());

        BeanUtils.copyProperties(config, data);

        return Result.success(data);
    }

    @PatchMapping("/layout")
    @ApiOperation("修改布局")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 3)
    public Result<String> editLayout(@RequestBody LayoutDTO data) {
        Project project = new Project();
        BeanUtils.copyProperties(data, project);
        project.setId(1);
        projectMapper.updateById(project);
        return Result.success();
    }

    @GetMapping("/layout")
    @ApiOperation("获取布局配置")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 4)
    public Result getLayout() {
        Project config = projectMapper.selectById(1);

        LayoutDTO data = new LayoutDTO();
        BeanUtils.copyProperties(config, data);

        return Result.success(data);
    }

    @GetMapping("/system")
    @ApiOperation("获取系统信息")
    @ApiOperationSupport(author = "刘宇阳 | liuyuyang1024@yeah.net", order = 5)
    public Result getSystemInfo() {
        SystemDTO data = projectService.getSystemInfo();
        return Result.success(data);
    }
}
