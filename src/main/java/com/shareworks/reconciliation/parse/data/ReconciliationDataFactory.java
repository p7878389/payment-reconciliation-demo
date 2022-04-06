package com.shareworks.reconciliation.parse.data;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author martin.peng
 */
@Component("ReconciliationDataFactory")
public class ReconciliationDataFactory {

    private static final Map<PaymentChannelEnums, DataParseStrategy> RECONCILIATION_DATA_FACTORY_MAP = new ConcurrentHashMap<>();

    public ReconciliationDataFactory(List<DataParseStrategy> dataParseStrategyList) {
        if (MapUtils.isEmpty(RECONCILIATION_DATA_FACTORY_MAP)) {
            RECONCILIATION_DATA_FACTORY_MAP.putAll(dataParseStrategyList.stream().collect(Collectors.toMap(DataParseStrategy::getPaymentChannel, dataParseStrategy -> dataParseStrategy)));
        }
    }

    public static DataParseStrategy getReconciliationDataParse(PaymentChannelEnums channel) {
        return RECONCILIATION_DATA_FACTORY_MAP.get(channel);
    }
}
