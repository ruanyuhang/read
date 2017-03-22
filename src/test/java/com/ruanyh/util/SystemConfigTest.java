package com.ruanyh.util;

import com.ruanyh.util.common.PropertiesUtils;
import org.junit.Test;

/**
 * Created by ruanyh on 17/3/22.
 */
public class SystemConfigTest {
    @Test
    public void testGet() {
        String val = PropertiesUtils.get("system.ftp.host");
        System.out.println("@system.ftp.host=" + val);
    }

}
