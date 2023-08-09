package selab.desktop.resource_management.controller.fundManagement;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.fundManagement.Vo.LogVo;
import selab.desktop.resource_management.service.fundManagement.LogService;
import selab.desktop.resource_management.utils.JsonResult;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Tag(name = "日志管理Controller")
@CrossOrigin(origins = "http://127.0.0.1:8080")
@RequestMapping("/logs")
@RestController
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping
    @Operation(summary = "日期分页分时间查询")
    public JsonResult<Page<LogVo>>getAllLogByPageAndDate(@RequestParam String updateTime,
                                                         @Parameter(description = "当前页数") int page,
                                                         @Parameter(description = "每页页数") int pageSize
                                                         ){
        Page<LogVo> logVoPage = new Page<>(page,pageSize);
        QueryWrapper<LogVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("update_time",updateTime);
        logService.page(logVoPage,queryWrapper);
        return new JsonResult<>(JsonResult.SUCCESS,null,logVoPage);
    }
    @GetMapping("/page")
    @Operation(summary = "查询全部")
    public JsonResult<Page<LogVo>>getAllLogByPage(@Parameter(description = "当前页数") int page,
                                                  @Parameter(description = "每页页数") int pageSize){
        QueryWrapper<LogVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("update_time");
        Page<LogVo> logVoPage = new Page<>(page, pageSize);
        logService.page(logVoPage,queryWrapper);
        return new JsonResult<>(JsonResult.SUCCESS,null,logVoPage);
    }

}
