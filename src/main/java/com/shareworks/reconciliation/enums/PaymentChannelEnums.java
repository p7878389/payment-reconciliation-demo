package com.shareworks.reconciliation.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author martin.peng
 */
@AllArgsConstructor
@Getter
public enum PaymentChannelEnums implements BaseEnums<PaymentChannelEnums, String> {
    ALIPAY("ALIPAY", "支付宝", "csv"),
    WECHAT("WECHAT", "微信", "txt"),
    WPS("WPS", "watchman payment systems", "txt"),
    ;

    private final String name;

    private final String desc;

    private final String fileType;

    @Override
    public String getCode() {
        return getName();
    }
}
