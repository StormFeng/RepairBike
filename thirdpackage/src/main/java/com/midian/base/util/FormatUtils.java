package com.midian.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 */

public class FormatUtils {
    public static boolean isMobile(String mobiles){

        Pattern p = Pattern.compile("((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\\\d{1}-?\\\\d{8}$)|(^0[3-9] {1}\\\\d{2}-?\\\\d{7,8}$)|(^0[1,2]{1}\\\\d{1}-?\\\\d{8}-(\\\\d{1,4})$)|(^0[3-9]{1}\\\\d{2}-? \\\\d{7,8}-(\\\\d{1,4})$))");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
}
