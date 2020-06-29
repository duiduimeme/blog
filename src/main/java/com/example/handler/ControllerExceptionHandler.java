package com.example.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//ControllerAdvice拦截所有标注controller的控制器，拦截器
@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //拦截的信息是Exception级别的
    //该类中，可以定义多个方法，不同的方法处理不同的异常，例如专门处理空指针的方法、专门处理数组越界的方法...，
    // 也可以直接向上面代码一样，在一个方法中处理所有的异常信息
    @ExceptionHandler(Exception.class)//异常处理的方法
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
        logger.error("Request URL :{},Exception:{}",request.getRequestURL(),e);
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            //对于带指定状态码的拦截器不拦截
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        //ModelAndView构造方法可以指定返回的页面名称
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        //通过setViewName()方法跳转到指定的页面
        return mv;
    }
}
