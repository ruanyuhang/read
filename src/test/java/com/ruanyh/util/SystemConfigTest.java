package com.ruanyh.util;

import com.ruanyh.util.common.SystemConfig;
import org.junit.Test;

/**
 * Created by ruanyh on 17/3/22.
 */
public class SystemConfigTest {
    @Test
    public void testGet() {
        System.out.println("@value=" + SystemConfig.get("system.ftp.host"));
        System.out.println("@value=" + SystemConfig.get("system.ftp.username"));
        System.out.println("@value=" + SystemConfig.get("system.ftp.password"));
        System.out.println("@value=" + SystemConfig.get("system.ftp.password0"));
    }

}
