package selab.desktop.resource_management.fundManagement.service.FundsServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.fundManagement.domain.Vo.FundsVo;
import selab.desktop.resource_management.fundManagement.mapper.FundsMapper;
import selab.desktop.resource_management.fundManagement.service.FundsService;

@Service
public class FundsServiceImpl extends ServiceImpl<FundsMapper, FundsVo> implements FundsService {
}
