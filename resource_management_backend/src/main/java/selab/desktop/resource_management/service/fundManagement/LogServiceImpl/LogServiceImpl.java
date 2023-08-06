package selab.desktop.resource_management.service.fundManagement.LogServiceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.fundManagement.Vo.LogVo;
import selab.desktop.resource_management.mapper.fundManagement.LogMapper;
import selab.desktop.resource_management.service.fundManagement.LogService;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper,LogVo> implements LogService {
}
