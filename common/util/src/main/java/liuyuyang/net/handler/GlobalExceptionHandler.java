package liuyuyang.net.handler;

import liuyuyang.net.execption.GuiguException;
import liuyuyang.net.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 全局异常处理类
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> error(GuiguException e) {
        e.printStackTrace();

        return Result.error(e.getMessage());
    }
}
