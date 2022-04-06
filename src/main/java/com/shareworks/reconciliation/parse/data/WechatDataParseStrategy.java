package com.shareworks.reconciliation.parse.data;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import com.shareworks.reconciliation.parse.ReconciliationDTO;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author martin.peng
 * 微信文件数据解析
 */
@Component("wechatReconciliationDataParse")
public class WechatDataParseStrategy implements DataParseStrategy<List<String>> {

    @Override
    public void dataParse(List<String> lineDataList) {
        List<ReconciliationDTO> reconciliationDTOList = new LinkedList<>();
        lineDataList.forEach(lineData -> {
            String line = lineData.replace("`", "");
            String[] dataArray = line.split(",");
            reconciliationDTOList.add(ReconciliationDTO.builder()
                    .platformUniquelyIdentifies(dataArray[2])
                    .platformOrderNo(dataArray[5])
                    .orderNo(dataArray[6])
                    .amount(dataArray[24])
                    .status(dataArray[9])
                    .paymentChannel(PaymentChannelEnums.WECHAT)
                    .build());
        });
        System.out.println("");
    }

    @Override
    public PaymentChannelEnums getPaymentChannel() {
        return PaymentChannelEnums.WECHAT;
    }
}
