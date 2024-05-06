package com.wl.emall.common.exception;

/**
 * @author wanglu
 */
public interface ExceptionInfoProvider {

    int getHttpStatus();

    String getCode();

    String getMessage(Object... args);

}
