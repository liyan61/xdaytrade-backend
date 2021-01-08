package xdaytrade.backend.controller;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xdaytrade.backend.service.IUserService;
import xdaytrade.backend.vo.RegisterVo;

@RestController
public class UserController {


    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public R register(
            @Validated RegisterVo registerVo,
            BindingResult validaResult){
        if (validaResult.hasErrors()){
            String error = validaResult.getFieldError()
                    .getDefaultMessage();
            return R.failed("注册失败");
        }
        System.out.println(registerVo);
        userService.register(registerVo);
        return R.ok("注册成功");
    }
}
