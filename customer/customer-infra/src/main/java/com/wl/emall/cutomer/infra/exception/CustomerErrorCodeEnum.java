package com.wl.emall.cutomer.infra.exception;

import com.wl.emall.common.exception.ExceptionInfoProvider;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wanglu
 */
@Getter
@AllArgsConstructor
public enum CustomerErrorCodeEnum implements ExceptionInfoProvider {
    BAD_REQUEST(400, "10000", "Bad request"),
    RESOURCE_NOT_FOUND(404, "10001", "Resource {0} not found"),
    ACCOUNT_NOT_EXISTS(500, "10002", "Account not exists"),
    ACCOUNT_ALREADY_EXISTS(500, "10003", "Account already exists"),
    BALANCE_NOT_ENOUGH(500, "10004", "Balance not enough"),
    INVALID_PARAM(500, "10005", "Invalid param"),
    INVENTORY_NOT_ENOUGH(500, "10006", "Inventory not enough");

    private final int httpStatus;

    private final String code;

    private final String message;

    @Override
    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }
}
