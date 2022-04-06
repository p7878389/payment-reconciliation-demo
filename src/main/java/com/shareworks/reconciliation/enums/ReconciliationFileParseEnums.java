package com.shareworks.reconciliation.enums;

import com.shareworks.reconciliation.parse.file.FileParseStrategy;
import com.shareworks.reconciliation.parse.file.TxtFileParseStrategy;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author martin.peng
 */

@Getter
public enum ReconciliationFileParseEnums implements BaseEnums<ReconciliationFileParseEnums, String> {
    TXT("txt") {
        @Override
        public FileParseStrategy apply() {
            return new TxtFileParseStrategy();
        }
    };

    private final String fileType;

    public abstract FileParseStrategy apply();

    ReconciliationFileParseEnums(String fileType) {
        this.fileType = fileType;
    }

    private static final List<ReconciliationFileParseEnums> RECONCILIATION_FILE_PARSE_ENUMS_LIST = Arrays.asList(ReconciliationFileParseEnums.values());

    public static FileParseStrategy getReconciliationFileParse(String fileType) {
        Optional<ReconciliationFileParseEnums> optional = RECONCILIATION_FILE_PARSE_ENUMS_LIST.stream()
                .filter(reconciliationFileParseEnums -> reconciliationFileParseEnums.getFileType().equals(fileType))
                .findFirst();
        Assert.isTrue(optional.isPresent(), "ReconciliationFileParse by fileType " + fileType + " not found");
        return optional.get().apply();
    }

    @Override
    public String getCode() {
        return getFileType();
    }
}
