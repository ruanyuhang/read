package com.ruanyh.util;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.units.JustNow;
import org.ocpsoft.prettytime.units.Millisecond;
import org.ocpsoft.prettytime.units.Second;

import java.util.Date;

public class PrettyTimeUtils {
    private static final PrettyTime PRETTY_TIME = new PrettyTime();

    static {
        PRETTY_TIME.removeUnit(JustNow.class);
        PRETTY_TIME.removeUnit(Second.class);
        PRETTY_TIME.removeUnit(Millisecond.class);
    }

    private PrettyTimeUtils() {}

    public static String format(Date date) {
        return PRETTY_TIME.format(date);
    }
}
