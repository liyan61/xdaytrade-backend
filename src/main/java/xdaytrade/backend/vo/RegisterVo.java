package xdaytrade.backend.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RegisterVo implements Serializable {


    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp ="^\\w{6,10}$",message = "密码在6到10位之间")
    private String password;
    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp ="^1\\d{10}$",message = "手机号格式不正确")
    private String phone;
    @NotBlank(message = "邮箱不能为空")
    private String email;
}
