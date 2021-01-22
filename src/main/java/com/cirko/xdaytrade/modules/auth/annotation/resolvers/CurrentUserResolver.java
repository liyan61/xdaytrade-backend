package com.cirko.xdaytrade.modules.auth.annotation.resolvers;

import com.cirko.xdaytrade.modules.auth.AuthConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import com.cirko.xdaytrade.dao.user.UserRepository;
import com.cirko.xdaytrade.entity.user.User;
import com.cirko.xdaytrade.modules.auth.annotation.CurrentUser;

@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数类型是User并且有CurrentUser注解则支持
        return parameter.getParameterType().isAssignableFrom(User.class) && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 取出鉴权时存入的登录用户Id
        Long currentUserId = (Long)webRequest.getAttribute(AuthConstants.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            // 从数据库中查询并返回
            return userRepository.findById(currentUserId);
        }
        throw new MissingServletRequestPartException(AuthConstants.CURRENT_USER_ID);
    }
}
