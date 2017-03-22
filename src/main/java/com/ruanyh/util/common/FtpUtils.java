package com.ruanyh.util.common;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class FtpUtils {
    private static final String FTP_HOST = SystemConfig.get("system.ftp.host");
    private static final String FTP_USERNAME = SystemConfig.get("system.ftp.username");
    private static final String FTP_PASSWORD = SystemConfig.get("system.ftp.password");
    private static final int FTP_PORT = FTPClient.DEFAULT_PORT;

    /**
     * 私有的构造方法,不允许实例化
     */
    private FtpUtils() {}


    public static boolean upload(String filePath) throws IOException {
        boolean result = false;
        FTPClient client = new FTPClient();
        InputStream is = null;
        try {
            client.connect(FTP_HOST, FTP_PORT);                     // 连接FTP服务器
            client.login(FTP_USERNAME, FTP_PASSWORD);               // 登录
            client.setFileType(FTP.BINARY_FILE_TYPE);               // 文件类型
            client.enterLocalPassiveMode();
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);   // 文件传输模式

            int reply = client.getReplyCode();                      // 回复代码
            System.out.println("ftp reply :" + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return result;
            }

            // TODO 获取配置中的目录

            client.changeWorkingDirectory("/tmp");                  // 定位到目录

            File file = new File(filePath);                         // 本地文件
            is = FileUtils.openInputStream(file);                   // 转换为流
            result = client.storeFile(file.getName(), is);          // 上传
        } finally {
            IOUtils.closeQuietly(is);
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        }
        return result;
    }

    public static void download() {

    }

}
