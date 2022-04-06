package com.shareworks.reconciliation.enums;

/**
 * @author martin.peng
 */
public interface BaseEnums<T extends Enum, K> {

    K getCode();
}
