package liuyuyang.net.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * oss平台
 *
 * @author laifeng
 * @version 1.0
 * @date 2024/12/10 20:51
 */
@Getter
@AllArgsConstructor
public enum EOssPlatform implements BasicEnum<String> {
    HUAWEI("huawei", "华为云OBS"),

    QINIU("qiniu", "七牛云Kodo"),

    ALIYUN("aliyun", "阿里云OSS"),
    TENCENT("tencent", "腾讯云COS"),
    MINIO("minio", "MinIO"),
    LOCAL_PLUS("local-plus", "本地存储");

    private final String value;
    private final String desc;
}
