package com.wl.emall.common.exception.util;

import com.wl.emall.common.exception.EmallCommonException;
import com.wl.emall.common.exception.ExceptionInfoProvider;

/**
 * @author wanglu
 */
public class AssertionUtil {

    public static void assertTrue(boolean condition, ExceptionInfoProvider errorCode, Object... args){
        if(!condition){
            throw new EmallCommonException(errorCode, args);
        }
    }

    public static void assertNotNull(Object obj, ExceptionInfoProvider errorCode, Object... args) {
        if (obj == null) {
            throw new EmallCommonException(errorCode, args);
        }
    }

    public static void assertAllNotNull(Object[] objs, ExceptionInfoProvider errorCode, Object... args) {
        for (Object obj : objs) {
            if (obj == null) {
                throw new EmallCommonException(errorCode, args);
            }
        }
    }

}
