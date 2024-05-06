package com.wl.emall.merchant.infra.exception;

import com.wl.emall.common.exception.ExceptionInfoProvider;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanglu
 */
@AllArgsConstructor
@Getter
public enum MerchantErrorCodeEnum implements ExceptionInfoProvider {
    BAD_REQUEST(400, "20000", "Bad request"),
    RESOURCE_NOT_FOUND(404, "20001", "Resource {0} not found"),
    ACCOUNT_NOT_EXISTS(500, "20002", "Account not exists"),
    ACCOUNT_ALREADY_EXISTS(500, "20003", "Account already exists"),
    BALANCE_NOT_ENOUGH(500, "20004", "Balance not enough"),
    INVALID_PARAM(500, "20005", "Invalid param"),
    INVENTORY_NOT_ENOUGH(500, "20006", "Inventory not enough"),
    RESOURCE_ALREADY_EXISTS(500, "20007", "Resource already exists");

    private final int httpStatus;

    private final String code;

    private final String message;

    @Override
    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
