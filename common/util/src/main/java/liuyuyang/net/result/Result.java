package liuyuyang.net.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code; // 响应码，200 代表成功; 400 代表失败
    private String msg; // 响应码 描述字符串
    private T data; // 返回的数据

    // 成功响应
    public static Result<String> success() {
        return new Result<>(200, "success", null);
    }

    public static <T> Result<T> success(String msg) {
        return new Result<>(200, msg, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static Result<Map<String, Object>> ok(Map<String, Object> data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    // 失败响应
    public static Result<String> error() {
        return new Result<>(400, "error", null);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(400, msg, null);
    }
}
