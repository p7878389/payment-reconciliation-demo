package com.shareworks.reconciliation.parse.file;

import com.shareworks.reconciliation.enums.PaymentChannelEnums;
import com.shareworks.reconciliation.parse.data.ReconciliationDataFactory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author martin.peng
 * 文本文件解析
 */
@Getter
@Setter
public class TxtFileParseStrategy implements FileParseStrategy {

    private RandomAccessFile randomAccessFile;
    private FileChannel fileChannel;

    /**
     * 支付渠道
     */
    private PaymentChannelEnums paymentChannel;

    /**
     * 对账文件大小
     */
    private Long fileLength;
    /**
     * 排除头信息
     */
    boolean excludeHeader = true;

    /**
     * 分片读取开始结束标记点，防止完整行信息只读到一般的问题
     */
    private List<StartEndPair> startEndPairList;

    private static final int bufferSize = 1024 * 1024;

    void init(String filePath, PaymentChannelEnums paymentChannel) throws IOException {
        randomAccessFile = new RandomAccessFile(filePath, "r");
        fileLength = randomAccessFile.length();
        fileChannel = randomAccessFile.getChannel();
        this.paymentChannel = paymentChannel;
        startEndPairList = new ArrayList<>();
    }

    @Override
    public void parseFile(String filePath, PaymentChannelEnums paymentChannel) throws IOException {
        init(filePath, paymentChannel);
        calculateStartEnd(0, bufferSize);
        for (StartEndPair startEndPair : startEndPairList) {
            long start = startEndPair.start;
            long sliceSize = startEndPair.end - startEndPair.start + 1;

            MappedByteBuffer mapBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, sliceSize);
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                List<String> lineDataList = new ArrayList<>(500);
                parseByte2String(sliceSize, mapBuffer, bos, lineDataList);
                ReconciliationDataFactory.getReconciliationDataParse(getPaymentChannel()).dataParse(lineDataList);
            }
        }
        close(randomAccessFile, fileChannel);
    }


    /**
     * byte转字符串
     *
     * @param sliceSize    预计读取byte大小
     * @param mapBuffer
     * @param bos
     * @param lineDataList
     */
    private void parseByte2String(long sliceSize, MappedByteBuffer mapBuffer, ByteArrayOutputStream bos, List<String> lineDataList) {
        byte[] readBuff = new byte[1024 * 1024];
        for (int offset = 0; offset < sliceSize; offset += bufferSize) {
            int readLength;
            if (offset + bufferSize <= sliceSize) {
                readLength = bufferSize;
            } else {
                readLength = (int) (sliceSize - offset);
            }
            mapBuffer.get(readBuff, 0, readLength);
            for (int i = 0; i < readLength; i++) {
                byte tmp = readBuff[i];
                //判断是否指定字符串结尾
                if (tmp == '\n' || tmp == '\r') {
                    String linData = handle(bos.toByteArray());
                    if (StringUtils.isNotBlank(linData)) {
                        lineDataList.add(linData);
                    }
                    bos.reset();
                } else {
                    bos.write(tmp);
                }
            }
        }
        if (bos.size() > 0) {
            String linData = handle(bos.toByteArray());
            if (StringUtils.isNotBlank(linData)) {
                lineDataList.add(linData);
            }
        }
        if (this.isExcludeHeader()) {
            lineDataList.remove(0);
        }
    }

    /**
     * 计算文件读取开始结束位置
     *
     * @param start
     * @param size
     * @throws IOException
     */
    private void calculateStartEnd(long start, long size) throws IOException {
        if (start > fileLength - 1) {
            return;
        }
        StartEndPair pair = new StartEndPair();
        pair.start = start;
        long endPosition = start + size - 1;
        if (endPosition >= fileLength - 1) {
            pair.end = fileLength - 1;
            startEndPairList.add(pair);
            return;
        }

        randomAccessFile.seek(endPosition);
        byte tmp = (byte) randomAccessFile.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            if (fileLength - 1 <= endPosition) {
                endPosition = fileLength - 1;
                break;
            }
            randomAccessFile.seek(endPosition);
            tmp = (byte) randomAccessFile.read();
        }
        pair.end = endPosition;
        startEndPairList.add(pair);
        calculateStartEnd(endPosition + 1, size);
    }

    private String handle(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private void close(RandomAccessFile inputFile, FileChannel inputFileChannel) throws IOException {
        if (Objects.nonNull(inputFileChannel)) {
            inputFileChannel.close();
        }
        if (Objects.nonNull(inputFile)) {
            inputFile.close();
        }
    }

    @Data
    private static class StartEndPair {
        public long start;
        public long end;
    }

    @Override
    public String getParseFileType() {
        return "txt";
    }
}
