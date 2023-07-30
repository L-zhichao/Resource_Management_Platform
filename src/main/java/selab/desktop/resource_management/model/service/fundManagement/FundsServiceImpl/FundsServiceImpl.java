package selab.desktop.resource_management.model.service.fundManagement.FundsServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.model.domain.fundManagement.Vo.FundsVo;
import selab.desktop.resource_management.model.mapper.fundManagement.FundsMapper;
import selab.desktop.resource_management.model.service.fundManagement.FundsService;

@Service
public class FundsServiceImpl extends ServiceImpl<FundsMapper, FundsVo> implements FundsService {
}
