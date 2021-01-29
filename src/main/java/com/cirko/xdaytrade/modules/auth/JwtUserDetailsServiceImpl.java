package com.cirko.xdaytrade.modules.auth;

import com.cirko.xdaytrade.dao.user.UserRepository;
import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.helpers.exception.GlobalException;
import com.cirko.xdaytrade.helpers.response.CodeMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new GlobalException(CodeMsg.NO_SUCH_USER.fill(username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
