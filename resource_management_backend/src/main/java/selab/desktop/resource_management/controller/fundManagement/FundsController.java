package selab.desktop.resource_management.controller.fundManagement;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.domain.fundManagement.Vo.FundsVo;
import selab.desktop.resource_management.domain.fundManagement.Vo.LogVo;
import selab.desktop.resource_management.service.fundManagement.FundsService;
import selab.desktop.resource_management.service.fundManagement.LogService;
import selab.desktop.resource_management.utils.JsonResult;
import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "资金管理controller")
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/fundsVo")
@RestController
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @Autowired
    private LogService logService;


    @GetMapping("/fundsVo/byName")
    @Operation(summary = "根据名字查询")
    @Transactional
    public JsonResult<FundsVo> getFundsByAsset(String name){
        QueryWrapper<FundsVo> wrapper = new QueryWrapper<>();
        wrapper.like("asset",name);

        return new JsonResult<>(JsonResult.SUCCESS,null,fundsService.getOne(wrapper));
    }
    @GetMapping("/fundsVo/{id}")
    @Operation(summary = "根据ID查询")
    @Transactional
    public JsonResult<FundsVo> getFundsById(@PathVariable Long id) {
        log.info("查询id"+id);

        return new JsonResult<>(JsonResult.SUCCESS, null, fundsService.getById(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询资金")
    @Transactional
    public JsonResult<Page<FundsVo>> page(@Parameter(description = "当前页数") int page,
                                          @Parameter(description = "每页页数") int pageSize) {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("update_time");
        Page<FundsVo> mPage = new Page<>(page, pageSize);
        fundsService.page(mPage,queryWrapper);
        log.info("查询所有资产");
        return new JsonResult<>(JsonResult.SUCCESS, null, mPage);
    }

    @GetMapping("/getCanBeUsed")
    @Operation(summary = "展示所有可支配资产")
    @Transactional
    public JsonResult<Page<FundsVo>> getCanBeUsedAsset(@Parameter(description = "当前页数") int page,
                                                       @Parameter(description = "每页页数") int pageSize) {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", "是").orderByDesc("update_time");
        log.info("展示所有可支配资产");
        Page<FundsVo> fundsVoPage = new Page<>(page, pageSize);
        fundsService.page(fundsVoPage,queryWrapper);
        return new JsonResult<>(JsonResult.SUCCESS, null, fundsVoPage);
    }


    @PostMapping("/update")
    @Operation(summary = "新增和修改资金")
    @Transactional
    public JsonResult<String> add(@RequestBody FundsVo fundsVo) {
        if (fundsVo.getId() == null) {
            fundsVo.setUpdateTime(new Date());
            log.info("增加资金");
            fundsService.save(fundsVo);

            LogVo logVo = new LogVo(null,new Date(),"增加资金",fundsVo.getId(),fundsVo.getAsset(),fundsVo.getAssetValue(),fundsVo.getJudge());
            logService.save(logVo);

            return new JsonResult<>(JsonResult.SUCCESS, null, null);
        } else {
            fundsVo.setUpdateTime(new Date());
            log.info("更新资金");
            fundsService.updateById(fundsVo);

            LogVo logVo = new LogVo(null,new Date(),"修改资金",fundsVo.getId(),fundsVo.getAsset(),fundsVo.getAssetValue(),fundsVo.getJudge());
            logService.save(logVo);

            return new JsonResult<>(JsonResult.SUCCESS, null, null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据主键ID删除某项资金")
    @Transactional
    public JsonResult<String> delete(@PathVariable Long id) {
        log.info("根据主键ID删除某项资金");
        log.info("删除成功");
        FundsVo fundsVo = fundsService.getById(id);
        fundsService.removeById(id);
        LogVo logVo = new LogVo(null,new Date(),"删除资金",fundsVo.getId(),fundsVo.getAsset(),fundsVo.getAssetValue(),fundsVo.getJudge());
        logService.save(logVo);

        return new JsonResult<>(JsonResult.SUCCESS, null, null);

    }

}
