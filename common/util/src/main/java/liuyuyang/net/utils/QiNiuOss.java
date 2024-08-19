package liuyuyang.net.utils;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

public final class QiNiuOss {
    String accessKey = "xpquCtc-v1t-M3wY3b8WVCVACS1viMpUNY6aZPzg";
    String secretKey = "1h-V_vCrOt2UIoHf4x_Rj4GxfjsW_IINRz0VyzFQ";
    String bucket = "thrive";
    Auth auth = Auth.create(accessKey, secretKey);

    public String getToken() {
        // 设置上传凭证的有效期（单位：秒），这里设置为1小时
        long expires = 3600;

        StringMap putPolicy = new StringMap();
        putPolicy.put("fsizeLimit", 10485760); // 文件大小限制为10MB
        putPolicy.put("mimeLimit", "image/jpeg; image/png"); // 仅允许上传JPEG和PNG图片文件

        String token = auth.uploadToken(bucket, null, expires, putPolicy);
        return token;
    }
}