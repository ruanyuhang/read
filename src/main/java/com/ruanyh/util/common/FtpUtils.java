package com.ruanyh.util.common;

import com.ruanyh.util.common.config.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
    private static final String UPLOAD_PATH = SystemConfig.get("system.ftp.home");

    /**
     * 私有的构造方法,不允许实例化
     */
    private FtpUtils() {}

    /**
     * 上传
     * @param localFilePath
     * @return
     * @throws IOException
     */
    public static boolean upload(String localFilePath) {
        boolean result = false;
        FTPClient client = new FTPClient();
        InputStream is = null;
        try {
            client.connect(FTP_HOST, FTP_PORT);                     // 连接FTP服务器
            client.login(FTP_USERNAME, FTP_PASSWORD);               // 登录
            client.setFileType(FTP.BINARY_FILE_TYPE);               // 文件类型,二进制
            client.enterLocalPassiveMode();                         // 被动模式
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);   // 文件传输模式

            int reply = client.getReplyCode();                      // 回复代码
            System.out.println("ftp reply :" + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return result;
            }

            String dir = System.getProperty("java.io.tmpdir");      // 操作系统临时目录
            if (StringUtils.isNotBlank(UPLOAD_PATH)) {
                dir = UPLOAD_PATH;
            }
            client.changeWorkingDirectory(dir);                     // 定位到文件上传路径

            File file = new File(localFilePath);                    // 本地文件
            is = FileUtils.openInputStream(file);                   // 转换为流
            result = client.storeFile(file.getName(), is);          // 上传
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                IOUtils.closeQuietly(is);
            }
            if (client.isConnected()) {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void download() {

    }

}
