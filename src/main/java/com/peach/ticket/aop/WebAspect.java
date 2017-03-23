package com.peach.ticket.aop;

import com.peach.ticket.domain.User;
import com.peach.ticket.web.response.ResponseCode;
import com.peach.ticket.web.response.ResponseVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * Created by jwliu on 2017/2/16.
 */
@Aspect
@Component
public class WebAspect {

    private String env = "prod";

    /**
     * 定义拦截规则：拦截com.xjj.web.controller包下面的所有类中，有@RequestMapping注解的方法。
     */
    @Pointcut("execution(* com.peach.ticket.web..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void controllerMethodPointcut() {
    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("controllerMethodPointcut()") //指定拦截器规则；也可以直接把“execution(* com.xjj.........)”写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) {
        long beginTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod(); //获取被拦截的方法
        String methodName = method.getName(); //获取被拦截的方法名

        Object result = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        if (isLoginRequired(method)) {
            if (!isLogin(request)) {
                result = new ResponseVo(ResponseCode.UNAUTHORIZED, "当前用户未登录");
            }
        }

        try {
            if (result == null) {
                // 一切正常的情况下，继续执行被拦截的方法
                result = pjp.proceed();
            }
        } catch (Throwable e) {
        }

        return result;
    }

    /**
     * 判断一个方法是否需要登录
     *
     * @param method
     * @return
     */
    private boolean isLoginRequired(Method method) {
        if (!env.equals("prod")) { //只有生产环境才需要登录
            return false;
        }

        Class c = method.getDeclaringClass();
        if (c.getName().indexOf("AccountController") >= 0) {
            return false;
        }

        return true;
    }

    //判断是否已经登录
    private boolean isLogin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("User");

        if (user == null) {
            return false;
        }

        if (user.getId() == 0L) {
            return false;
        }

        return true;
    }
}
