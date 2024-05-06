package com.wl.emall.common.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author wanglu
 */
public class DateTimeUtil {

    public static LocalDate convertToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
