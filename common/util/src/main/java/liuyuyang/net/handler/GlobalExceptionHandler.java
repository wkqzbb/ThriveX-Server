package liuyuyang.net.handler;

import liuyuyang.net.execption.YuYangException;
import liuyuyang.net.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义的异常
    @ResponseBody
    @ExceptionHandler(YuYangException.class)
    public Result<Object> yuyangException(YuYangException e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }

    // 处理所有异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> exception(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
