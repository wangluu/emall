package com.wl.emall.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wanglu
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private String code;

    private String message;

}
