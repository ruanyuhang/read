package com.ruanyh.util;

import org.junit.Test;

import java.util.Date;

/**
 * Created by ruanyh on 17/3/7.
 */
public class PrettyTimeUtilsTest {

    @Test
    public void testPrettyTime() {
        String time = PrettyTimeUtils.format(new Date());
        System.out.println(time);
    }

}
