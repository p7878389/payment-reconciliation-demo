package com.shareworks.reconciliation.parse;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import com.shareworks.reconciliation.enums.ReconciliationFileParseEnums;
import com.shareworks.reconciliation.parse.file.FileParseStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author martin.peng
 */
@Component
public class PaymentReconciliationParse {

    public void reconciliationParse(PaymentChannelEnums paymentChannel, String path, String fileType) throws IOException {
        FileParseStrategy fileParseStrategy = ReconciliationFileParseEnums.getReconciliationFileParse(fileType);
        fileParseStrategy.parseFile(path, paymentChannel);
    }
}
