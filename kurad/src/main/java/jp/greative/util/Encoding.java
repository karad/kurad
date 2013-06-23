package jp.greative.util;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;

/**
 * Encoding utility
 */
public class Encoding {

    /**
     * convert to utf-8 string
     * @param src
     * @return
     */
    public static String convert(String src) {
        NumericEntityUnescaper neu = new NumericEntityUnescaper();
        return neu.translate(src);
    }

}


