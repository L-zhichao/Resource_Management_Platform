package selab.desktop.resource_management.service.userManagement.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import selab.desktop.resource_management.domain.userManagement.log.UserLog;
import selab.desktop.resource_management.exception.userManagment.UserLogInsertException;
import selab.desktop.resource_management.mapper.userManagement.UserLogMapper;
import selab.desktop.resource_management.service.userManagement.UserLogService;

import java.util.List;

/**

 * @className: UserLogServiceImpl
 * @author: lizhichao
 * @description: TODO
 * @date: 2023/08/03 10:42
 * @version: 1.0
 */
@RequiredArgsConstructor
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {
    private final UserLogMapper userLogMapper;

    @Override
    public void insertLog(UserLog userLog) {

        int rows = userLogMapper.insert(userLog);
        if(rows != 1){
            throw new UserLogInsertException("日志记录未知异常");
        }
    }

    @Override
    public Long queryForCount() {
        QueryWrapper<UserLog> userLogQueryWrapper = new QueryWrapper<>();
        Long count = userLogMapper.selectCount(userLogQueryWrapper);
        return count;
    }

    @Override
    public List<UserLog> queryAll(Long current, Long size) {
        Page<UserLog> userLogPage = new Page<>(current,size);
        QueryWrapper<UserLog> userLogQueryWrapper = new QueryWrapper<>();
        Page<UserLog> userLogPage1 = userLogMapper.selectPage(userLogPage, userLogQueryWrapper);
        List<UserLog> records = userLogPage1.getRecords();
        return records;
    }


}
