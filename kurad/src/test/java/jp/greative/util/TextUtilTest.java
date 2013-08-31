package jp.greative.util;

import static org.junit.Assert.*;
import org.hamcrest.core.*;
import org.junit.Test;

/**
 * DESCRIPTION
 *
 * @author harakazuhiro
 * @since 2013/08/31 13:32
 */

public class TextUtilTest {
    @Test
    public void testToUpperCase() throws Exception {
        String test001 = "id";
        String right = "Id";
        assertThat(TextUtil.toUpperCase(test001), IsEqual.equalTo(right));
    }

    @Test
    public void test() {

    }
}
