package com.ruanyh.util.common;

import com.ruanyh.util.common.config.SystemConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * FTP工具
 */
public class FtpUtils {
    private static final String FTP_HOST = SystemConfig.get("system.ftp.host");
    private static final String FTP_USERNAME = SystemConfig.get("system.ftp.username");
    private static final String FTP_PASSWORD = SystemConfig.get("system.ftp.password");
    private static final int FTP_PORT = FTPClient.DEFAULT_PORT;

    /**
     * 私有的构造方法,不允许实例化
     */
    private FtpUtils() {}


    /**
     * 上传
     * @param original
     * @param target
     * @return
     */
    public static Boolean upload(String original, String target) {
        boolean success = false;
        FTPClient client = new FTPClient();
        InputStream input = null;
        try {
            client.connect(FTP_HOST, FTP_PORT);
            client.login(FTP_USERNAME, FTP_PASSWORD);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            int reply = client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                return success;
            }

            File file = new File(original);
            input = new FileInputStream(original);

            if (client.changeWorkingDirectory("/")) {
                String ftpHome = target;
                if (StringUtils.isNotBlank(ftpHome)) {
                    boolean changeDir = client.changeWorkingDirectory(ftpHome);
                    if (!changeDir) {
                        String userHome = System.getProperty("user.home");
                        System.out.println("切换FTP工作目录失败, 用户主目录为" + userHome + ", 请确认" + ftpHome + "是否在用户主目录下");
                    }
                }
                client.setControlEncoding("UTF-8");
                success = client.storeFile(file.getName(), input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
            if (client.isConnected()) {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

    /**
     * 下载文件
     *
     * @param fileName
     *            文件名
     * @param localDir
     *            下载本地路径
     * @return
     */
    public static File download(String fileName, String localDir) {
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;
        File file = null;
        boolean resu = false;
        try {
            client.connect(FTP_HOST, FTP_PORT);
            client.login(FTP_USERNAME, FTP_PASSWORD);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
            client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

            file = new File(localDir + File.separator + fileName);
            fos = new FileOutputStream(file);
            resu = client.changeWorkingDirectory("/");
            String ftpHome = "";
            if (StringUtils.isNotBlank(ftpHome)) {
                resu = client.changeWorkingDirectory(ftpHome);
            }
            resu = client.retrieveFile(fileName, fos);
            fos.flush();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(String.format("download file error. fileName:%s%s error: %s", localDir, fileName, e.getMessage()));
        } finally {
            IOUtils.closeQuietly(fos);
            if (client.isConnected()) {
                try {
                    client.logout();
                    client.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        if (resu) {
            return file;
        } else {
            System.err.println("FTP DownLoad Fail:" + fileName);
            return null;
        }
    }

}
