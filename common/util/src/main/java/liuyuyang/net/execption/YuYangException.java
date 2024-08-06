package liuyuyang.net.execption;

import lombok.Data;

@Data
public class YuYangException extends RuntimeException {
    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code
     * @param message
     */
    public YuYangException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "YuYangException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}