package selab.desktop.resource_management.mapper.fundManagement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;
import selab.desktop.resource_management.domain.fundManagement.Vo.LogVo;

@Mapper
public interface LogMapper extends BaseMapper<LogVo> {
}
