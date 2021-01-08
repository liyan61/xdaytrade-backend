package xdaytrade.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xdaytrade.backend.model.User;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
