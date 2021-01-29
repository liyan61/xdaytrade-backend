package com.cirko.xdaytrade.service.user.Impl;

import com.aliyuncs.utils.StringUtils;
import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.entity.user.UserDetail;
import com.cirko.xdaytrade.helpers.exception.GlobalException;
import com.cirko.xdaytrade.helpers.response.CodeMsg;
import com.cirko.xdaytrade.utils.SendSMSUtil;
import com.cirko.xdaytrade.utils.VCodeUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cirko.xdaytrade.dao.user.UserRepository;
import com.cirko.xdaytrade.modules.auth.JwtTokenUtil;
import com.cirko.xdaytrade.service.user.UserService;

import java.security.Permission;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired(required = false)
    private VCodeUtill vCodeUtill;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    @Qualifier("jwtUserDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }
    @Override
    public String loginPhone(String phoneNumber,String code) {
        if (userRepository.findByPhone(phoneNumber)==null){
            //跳转注册
            String pwd = vCodeUtill.verifyCode(6);
            register(phoneNumber,pwd);
            return "";
        }
        if (!isValidMobileNo(phoneNumber)){
            //手机号格式错误
            throw new GlobalException(CodeMsg.MOBILE_ERROR.fill(phoneNumber));
        }
        Map<Object, Object> map=redisTemplate.opsForHash().entries(phoneNumber);
        String serverCheckCode =(String) map.get(phoneNumber);
        if (serverCheckCode == null || serverCheckCode.equals("")) {
            return "CodeError";
        }
        // 验证码不匹配
        if (!code.equals(serverCheckCode)) {
            return "CodeError";
        }else {
            //如果验证成功就删除验证码
            redisTemplate.opsForHash().delete(phoneNumber,phoneNumber);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        return jwtTokenUtil.generateToken(userDetails);
    }
    @Override
    public String loginEmail(String email,String code){
        if (isValidEmail(email)){
            throw new GlobalException(CodeMsg.EMAIL_ERROR.fill(email));
        }
        Map<Object, Object> map=redisTemplate.opsForHash().entries(email);
        String VCode =(String) map.get(email);
        if (VCode == null || VCode.equals("")) {
            return "CodeError";
        }
        // 验证码不匹配
        if (!code.equals(VCode)) {
            return "CodeError";
        }else {
            //如果验证成功就删除验证码
            redisTemplate.opsForHash().delete(email,email);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtTokenUtil.generateToken(userDetails);
    }
    @Override
    public User register(String username,String pwd){
        if (userRepository.findByUsername(username)!=null){
            throw new GlobalException(CodeMsg.SUCH_USER.fill(username));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user =new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(pwd));
        return userRepository.save(user);
    }
    @Override
    public User registerPhone(String phone,String pwd,String code) {
        //非空判断
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(pwd)
                || StringUtils.isEmpty(code)) {
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(redisCode)) {
            throw new GlobalException(CodeMsg.BIND_ERROR);
        }
        //判断手机号是否重复，表里面存在相同手机号不进行添加
        User user1= userRepository.findByPhone(phone);
        if (user1!=null){
            throw new GlobalException(CodeMsg.MOBILE_EXIST);
        }
        //数据添加数据库中
        User user =new User();
        user.setUsername(phone);
        user.setPassword(pwd);
        user.setPhone(phone);
        userRepository.save(user);
        return user;
    }
    @Override
    public User registerEmail(String email,String pwd,String code){
        //非空判断
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(pwd)
                || StringUtils.isEmpty(code)) {
            throw new GlobalException(CodeMsg.EMAIL_ERROR);
        }
        //判断验证码
        //获取redis验证码
        String redisEmailCode = redisTemplate.opsForValue().get(email);
        if(!code.equals(redisEmailCode)) {
            throw new GlobalException(CodeMsg.BIND_ERROR);
        }
        //判断邮箱是否重复，表里面存在相同邮箱不进行添加
        User user1= userRepository.findByPhone(email);
        if (user1!=null){
            throw new GlobalException(CodeMsg.EMAIL_EXIST);
        }
        //数据添加数据库中
        User user =new User();
        user.setUsername(email);
        user.setPassword(pwd);
        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findOneByPhone(String phone) { return userRepository.findByPhone(phone); }

    @Override
    public Optional<User> findOneById(Long id) { return userRepository.findById(id); }


    /**
     * 验证是否为手机号
     *
     * @param mobileNo
     * @return
     */
    public static boolean isValidMobileNo(String mobileNo) {
        // 1、(13[0-9])|(15[02789])|(18[679])|(17[0-9]) 13段 或者15段 18段17段的匹配
        // 2、\\d{8} 整数出现8次
        boolean flag = false;
        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher match = p.matcher(mobileNo);
        if (mobileNo != null) {
            flag = match.matches();
        }
        return flag;
    }
    /**
     * 验证是否为正确的邮箱号
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        // 1、\\w+表示@之前至少要输入一个匹配字母或数字或下划线 \\w 单词字符：[a-zA-Z_0-9]
        // 2、(\\w+\\.)表示域名. 如新浪邮箱域名是sina.com.cn
        // {1,3}表示可以出现一次或两次或者三次.
        String reg = "\\w+@(\\w+\\.){1,3}\\w+";
        Pattern pattern = Pattern.compile(reg);
        boolean flag = false;
        if (email != null) {
            Matcher matcher = pattern.matcher(email);
            flag = matcher.matches();
        }
        return flag;
    }
}

