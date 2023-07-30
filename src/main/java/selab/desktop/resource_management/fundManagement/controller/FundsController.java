package selab.desktop.resource_management.fundManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.service.FundsService;
import selab.desktop.resource_management.fundManagement.utils.JsonResult;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "资金管理controller")
@RequestMapping("/fundsVo")
@RestController
@CrossOrigin
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @GetMapping
    @Operation(summary = "展示目前总资产")
    @Transactional
    public JsonResult<List<FundsVo>> getAllFunds() {
        log.info("展示目前总资产");
        return new JsonResult<>(JsonResult.SUCCESS, null, fundsService.list());
    }

    @GetMapping("/fundsVo/{id}")
    @Operation(summary = "根据ID查询")
    @Transactional
    public JsonResult<FundsVo> getFundsById(@PathVariable Long id) {
        return new JsonResult<>(JsonResult.SUCCESS, null, fundsService.getById(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询资金")
    @Transactional
    public JsonResult<Page<FundsVo>> page(int page, int pageSize) {
        Page<FundsVo> mPage = new Page<>(page, pageSize);
        fundsService.page(mPage);
        return new JsonResult<>(JsonResult.SUCCESS, null, mPage);
    }

    @GetMapping("/getCanBeUsed")
    @Operation(summary = "展示所有可支配资产")
    @Transactional
    public JsonResult<List<FundsVo>> getCanBeUsedAsset() {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", "是");
        List<FundsVo> list = fundsService.list(queryWrapper);
        log.info("展示所有可支配资产");
        return new JsonResult<>(JsonResult.SUCCESS, null, list);
    }


    @PostMapping("/update")
    @Operation(summary = "新增和修改资金")
    @Transactional
    public JsonResult<String> add(@RequestBody FundsVo fundsVo) {
        if (fundsVo.getId() == null) {
            fundsVo.setUpdateTime(new Date());
            log.info("增加资金");
            fundsService.save(fundsVo);
            return new JsonResult<>(JsonResult.SUCCESS, null, "增加成功");
        } else {
            fundsVo.setUpdateTime(new Date());
            log.info("更新资金");
            fundsService.updateById(fundsVo);
            return new JsonResult<>(JsonResult.SUCCESS, null, "更新成功");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据主键ID删除某项资金")
    @Transactional
    public JsonResult<String> delete(@PathVariable Long id) {
        log.info("根据主键ID删除某项资金");
        log.info("删除成功");
        return new JsonResult<>(JsonResult.SUCCESS, null, "删除成功");

    }

}

