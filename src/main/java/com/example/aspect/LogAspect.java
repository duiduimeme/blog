package com.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    /*
    : --------------dobefore--------------
    controller输出------------index------------
    : --------------doAfter--------------
    : Result:{}index
     *
     在AOP中切面就是与业务逻辑独立，但又垂直存在于业务逻辑的代码结构中的通用功能组合；切面与业务逻辑相交的点就是切点；
     连接点就是把业务逻辑离散化后的关键节点；切点属于连接点，是连接点的子集；Advice（增强）就是切面在切点上要执行
     的功能增加的具体操作；在切点上可以把要完成增强操作的目标对象（Target）连接到切面里，这个连接的方式就叫织入。
     * */
    //日志记录器
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //切面 所有demo下的控制器都需要执行
    @Pointcut("execution(* com.example.demo.*.*(..))")
    public void log(){

    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //通过切面的方法获取方法名和参数
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+","+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        System.out.println(requestLog);
        logger.info("--------------dobefore--------------");
    }
    @After("log()")
    public void doAfter(){
//        logger.info("--------------doAfter--------------");
    }
    @AfterReturning(returning="result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}"+result);
    }
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
