package com.ruanyh.util;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Created by ruanyh on 17/3/14.
 */
public class JodaTimeTest {
    @Test
    public void testJodaTime() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
    }
}
