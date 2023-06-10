package com.splendor.notes.infrastructure.apiTemp;

import lombok.Builder;
import lombok.Data;

/**
 * @author splendor.s
 * @create 2023/6/10 上午10:34
 * @description 构建请求头对象
 */
@Data
@Builder
public class RequestHeader {
    private String sign ;
    private Long timestamp ;
    private String nonce;
}
