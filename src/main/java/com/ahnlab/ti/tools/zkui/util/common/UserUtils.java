package com.ahnlab.ti.tools.zkui.util.common;

import com.ahnlab.ti.tools.zkui.exception.user.InvalidPathException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtils {
    public static final String EMPTY_STRING = "";

    public static String convertMsToDate(long ms){
        Date date = new Date(ms);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String decodedData(byte[] data) throws UnsupportedEncodingException {
        if (data == null)
            return EMPTY_STRING;
        else
            return new String(data, "UTF-8");
    }

}
