package selab.desktop.resource_management.fundManagement.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.service.FundsService;
import selab.desktop.resource_management.fundManagement.utils.R;

import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "资金管理controller")
@RequestMapping("/fundsVo")
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @GetMapping("/fundsVo")
    @Operation(summary = "展示目前总资产")
    public R<List<FundsVo>> getAllFunds(){
        return R.success(fundsService.list());
    }

    @GetMapping
    @Operation(summary = "展示所有可支配资产")
    public R<List<FundsVo>> getCanBeUsedAsset() {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", 1);
        List<FundsVo> list = fundsService.list(queryWrapper);
        return R.success(list);
    }


    @PostMapping
    @Operation(summary = "新增和修改资金")
    public R<String> add(@RequestBody FundsVo fundsVo) {
        if (fundsVo.getId() == null) {
            fundsVo.setUpdateTime(new Date());
            fundsService.save(fundsVo);
            return R.success("增加成功");
        } else {
            fundsVo.setUpdateTime(new Date());
            fundsService.updateById(fundsVo);
            return R.success("更新成功");
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "根据主键ID删除某项资金")
    public R<String> delete(@PathVariable Long id) {
        fundsService.removeById(id);
        return R.success("删除成功");
    }

}

