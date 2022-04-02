package com.example.demo.servicebase.exception.handler;





import com.example.demo.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //指定出现什么异常会执行这个方法
//    @ExceptionHandler(Exception.class)
//    @ResponseBody//为了能够返回数据
//    public R error(Exception e){
//        e.printStackTrace();
//        return R.error().message("执行全局异常处理");
//    }
//特殊异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了能够返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }
    //自定义异常

    @ExceptionHandler(MyException.class)
    @ResponseBody//为了能够返回数据
    public R error(MyException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMessage());
    }
}
