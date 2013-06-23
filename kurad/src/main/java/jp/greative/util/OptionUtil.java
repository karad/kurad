package jp.greative.util;

import play.libs.F;
import java.util.List;

/**
 * Scala like option utility
 */
public class OptionUtil {

    /**
     * for default value
     * @param value
     * @param <A>
     * @return
     */
    public static <A> F.Option<A> apply(A value) {
        if(value != null) {
            return F.Option.Some(value);
        } else {
            return F.Option.None();
        }
    }

    /**
     * for list value
     * @param value
     * @param <A>
     * @return
     */
    public static <A> F.Option<List<A>> apply(List<A> value) {
        if(value != null && value.size() != 0) {
            return F.Option.Some(value);
        } else {
            return F.Option.None();
        }
    }

    /**
     * for empty string
     * @param value
     * @param <String>
     * @return
     */
    public static <String> F.Option<String> applyWithString(String value) {
        if(value != null && !value.equals("")) {
            return F.Option.Some(value);
        } else {
            return F.Option.None();
        }
    }

}
