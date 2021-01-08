package xdaytrade.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xdaytrade.backend.model.User;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username=#{username}")
    User findUserByUsername(String username);

}
