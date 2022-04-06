package com.shareworks.reconciliation.parse.file;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;

import java.io.IOException;

/**
 * @author martin.peng
 * 对账文件解析
 */
public interface FileParseStrategy {

    /**
     * 文件解析
     *
     * @param filePath
     * @param paymentChannel
     * @throws IOException
     */
    void parseFile(String filePath, PaymentChannelEnums paymentChannel) throws IOException;


    /**
     * 支持的文件解析类型
     *
     * @return
     */
    String getParseFileType();
}
