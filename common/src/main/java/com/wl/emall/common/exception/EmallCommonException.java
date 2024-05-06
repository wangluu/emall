package com.wl.emall.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wanglu
 */
@Getter
@Setter
public class EmallCommonException extends RuntimeException {

    private int httpStatus;

    private String code;

    private Object[] args;

    public EmallCommonException(ExceptionInfoProvider provider, Object... args) {
        super(provider.getMessage(args));
        this.code = provider.getCode();
        this.httpStatus = provider.getHttpStatus();
        this.args = args;
    }

    public EmallCommonException(ExceptionInfoProvider provider, Throwable throwable, Object... args) {
        super(provider.getMessage(args), throwable);
        this.httpStatus = provider.getHttpStatus();
        this.code = provider.getCode();
        this.args = args;
    }

}
