package com.adtec.rdc.base.common.template.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import com.adtec.rdc.base.common.template.MessageTemplate;

/**
 * @author: JTao
 * @date: 2018/11/30 17:16
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SmsMessageTemplate extends MessageTemplate implements Serializable {
    private static final long serialVersionUID =1133414L;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 短信签名
     */
    private String signName;

    /**
     * 模板
     */
    private String template;

    /**
     * 腾讯云参数回填
     */
    private String[] params;
}
