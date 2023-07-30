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
import selab.desktop.resource_management.fundManagement.utils.R;

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
    public R<List<FundsVo>> getAllFunds(){
        log.info("展示目前总资产");
        return R.success(fundsService.list());
    }
    @GetMapping("/fundsVo/{id}")
    @Operation(summary = "根据ID查询")
    @Transactional
    public R<FundsVo> getFundsById(@PathVariable Long id){
        return R.success(fundsService.getById(id));
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询资金")
    @Transactional
    public R<Page<FundsVo>> page(int page, int pageSize){
        Page <FundsVo> mPage = new Page<>(page,pageSize);
        fundsService.page(mPage);
        return R.success(mPage);
    }

    @GetMapping("/getCanBeUsed")
    @Operation(summary = "展示所有可支配资产")
    @Transactional
    public R<List<FundsVo>> getCanBeUsedAsset() {
        QueryWrapper<FundsVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("judge", "是");
        List<FundsVo> list = fundsService.list(queryWrapper);
        log.info("展示所有可支配资产");
        return R.success(list);
    }


    @PostMapping("/update")
    @Operation(summary = "新增和修改资金")
    @Transactional
    public R<String> add(@RequestBody FundsVo fundsVo) {
        if (fundsVo.getId() == null) {
            fundsVo.setUpdateTime(new Date());
            log.info("增加资金");
            fundsService.save(fundsVo);
            return R.success("增加成功");
        } else {
            fundsVo.setUpdateTime(new Date());
            log.info("更新资金");
            fundsService.updateById(fundsVo);
            return R.success("更新成功");
        }
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "根据主键ID删除某项资金")
    @Transactional
    public R<String> delete(@PathVariable Long id) {
        log.info("根据主键ID删除某项资金");
//        Long longId = Long.parseLong(id);
        boolean b = fundsService.removeById(id);
        if (b) {
            log.info("删除成功");
            return R.success("删除成功");

        } else {
            log.error("删除失败");
            return R.error("删除失败");
        }
    }

}

