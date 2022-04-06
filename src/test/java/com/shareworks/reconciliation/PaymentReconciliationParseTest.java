package com.shareworks.reconciliation;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import com.shareworks.reconciliation.parse.PaymentReconciliationParse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PaymentReconciliationParseTest {

    @Autowired
    private PaymentReconciliationParse paymentReconciliationParse;

    @Test
    public void parseData() throws IOException {
        String path = PaymentReconciliationParseTest.class.getClassLoader().getResource("wechat-reconciliation.txt").getPath();
        paymentReconciliationParse.reconciliationParse(PaymentChannelEnums.WECHAT, path, "txt");
    }
}
