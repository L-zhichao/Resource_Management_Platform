package selab.desktop.resource_management.fundManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.service.FundsService;
import selab.desktop.resource_management.fundManagement.utils.R;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "资金管理controller")
@RequestMapping("/fundsVo")
@RestController
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @GetMapping("/fundsVo")
    @Operation(summary = "展示目前总资产")
    public R<List<FundsVo>> getAllFunds(){
        log.info("展示目前总资产");
        return R.success(fundsService.list());
    }

    @GetMapping
    @Operation(summary = "展示所有可支配资产")
    public R<List<FundsVo>> getCanBeUsedAsset() {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", 1);
        List<FundsVo> list = fundsService.list(queryWrapper);
        log.info("展示所有可支配资产");
        return R.success(list);
    }


    @PostMapping
    @Operation(summary = "新增和修改资金")
    public R<String> add(@RequestBody FundsVo fundsVo) {
        if (fundsVo.getId() == null) {
            fundsVo.setUpdateTime(new Date());
            fundsService.save(fundsVo);
            log.info("增加资金");
            return R.success("增加成功");
        } else {
            fundsVo.setUpdateTime(new Date());
            fundsService.updateById(fundsVo);
            log.info("更新资金");
            return R.success("更新成功");
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "根据主键ID删除某项资金")
    public R<String> delete(@PathVariable Long id) {
        fundsService.removeById(id);
        log.info("根据主键ID删除某项资金");
        return R.success("删除成功");
    }

}

