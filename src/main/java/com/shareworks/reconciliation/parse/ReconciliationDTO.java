package com.shareworks.reconciliation.parse;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author martin.peng
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReconciliationDTO {

    /**
     * 平台订单号
     */
    private String platformOrderNo;

    /**
     * 系统订单号
     */
    private String orderNo;

    /**
     * 支付金额
     */
    private String amount;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 支付渠道
     */
    private PaymentChannelEnums paymentChannel;

    /**
     * 平台唯一标识
     */
    private String platformUniquelyIdentifies;

    /**
     * 支付时间
     */
    private Date payTime;

}
