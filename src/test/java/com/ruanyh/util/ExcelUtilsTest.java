package com.ruanyh.util;

import com.ruanyh.util.common.ExcelUtils;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by ruanyh on 17/3/14.
 */
public class ExcelUtilsTest {
    @Test
    public void testRead() {
        String path = "/Users/ruanyh/Documents/temp/data.xls";
        List<Map<String, String>> list = ExcelUtils.read(path);
        System.out.println(list.toString());
    }

}
