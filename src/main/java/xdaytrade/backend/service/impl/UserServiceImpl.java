package xdaytrade.backend.service.impl;

;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdaytrade.backend.mapper.UserMapper;
import xdaytrade.backend.model.User;
import xdaytrade.backend.service.IUserService;
import xdaytrade.backend.vo.RegisterVo;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(RegisterVo registerVo) {
        User u = userMapper.findUserByUsername(registerVo.getPhone());
        if (u!=null){
            throw new RuntimeException();
        }
        User user = new User();
        user.setUsername(registerVo.getPhone());
        user.setPhone(registerVo.getPhone());
        user.setPassword(registerVo.getPassword());
        user.setEmail(registerVo.getEmail());
        userMapper.insert(user);
    }
}
