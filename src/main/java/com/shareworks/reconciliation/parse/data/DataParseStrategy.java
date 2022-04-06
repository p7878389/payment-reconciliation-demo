package com.shareworks.reconciliation.parse.data;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;

/**
 * @author martin.peng
 */
public interface DataParseStrategy<T> {

    /**
     * 文件数据解析
     *
     * @param t
     */
    void dataParse(T t);

    /**
     * 对于支持的渠道
     *
     * @return
     */
    PaymentChannelEnums getPaymentChannel();
}
