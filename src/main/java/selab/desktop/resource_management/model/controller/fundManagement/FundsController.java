package selab.desktop.resource_management.model.controller.fundManagement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import selab.desktop.resource_management.model.domain.fundManagement.Vo.FundsVo;
import selab.desktop.resource_management.model.service.fundManagement.FundsService;
import selab.desktop.resource_management.model.utils.R;

import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "资金管理controller")
@RequestMapping("/fundsVo")
@RestController
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @GetMapping
    @Operation(summary = "展示目前总资产")
    public R<List<FundsVo>> getAllFunds(){
        log.info("展示目前总资产");
        return R.success(fundsService.list());
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询用户")
    public R<Page<FundsVo>> page(int page, int pageSize){
        Page <FundsVo> mPage = new Page<>(page,pageSize);
        fundsService.page(mPage);
        return R.success(mPage);
    }

    @GetMapping("/getCanBeUsed")
    @Operation(summary = "展示所有可支配资产")
    public R<List<FundsVo>> getCanBeUsedAsset() {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", "是");
        List<FundsVo> list = fundsService.list(queryWrapper);
        log.info("展示所有可支配资产");
        return R.success(list);
    }


    @PostMapping("/update")
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

