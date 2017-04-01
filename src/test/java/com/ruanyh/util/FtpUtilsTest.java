package com.ruanyh.util;


import com.ruanyh.util.common.FtpUtils;
import org.junit.Test;

public class FtpUtilsTest {

    @Test
    public void testUpload() {
        String filePath = "/Users/ruanyh/Downloads/video/video.mp4";
        FtpUtils.upload(filePath);
    }

}
