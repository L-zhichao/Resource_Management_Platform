package selab.desktop.resource_management.service.fundManagement.FundsServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.mapper.fundManagement.FundsMapper;
import selab.desktop.resource_management.service.fundManagement.FundsService;


@Service
public class FundsServiceImpl extends ServiceImpl<FundsMapper, selab.desktop.resource_management.domain.fundManagement.Vo.FundsVo> implements FundsService {
}
