package liuyuyang.net.handler;

import com.qiniu.common.QiniuException;
import liuyuyang.net.execption.CustomException;
import liuyuyang.net.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    // 处理自定义的异常
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public Result<Object> customException(CustomException e) {
        e.printStackTrace();
        return Result.error(e.getCode(), e.getMessage());
    }

    // 处理七牛云Oss异常
    @ResponseBody
    @ExceptionHandler(QiniuException.class)
    public Result<Object> qiniuException(QiniuException e) {
        e.printStackTrace();
        return Result.error(e.code(), e.error());
    }

    // 处理上传文件大小超过限制的异常
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return Result.error(400, "上传失败：文件大小超出最大限制");
    }

    // 处理所有异常
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> exception(Exception e) {
        e.printStackTrace();
        return Result.error(e.getMessage());
    }
}
