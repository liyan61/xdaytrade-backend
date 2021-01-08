package xdaytrade.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import xdaytrade.backend.model.User;
import xdaytrade.backend.vo.RegisterVo;

public interface IUserService extends IService<User> {
    void register(RegisterVo registerVo);

}
