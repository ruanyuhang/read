package com.ruanyh.util.common;

import com.ruanyh.util.common.config.SystemConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Arrays;

/**
 * FTP工具
 */
public class FtpUtils {
    private static final String FTP_HOST = SystemConfig.get("system.ftp.host");             // FTP主机IP
    private static final String FTP_USERNAME = SystemConfig.get("system.ftp.username");     // FTP登录用户名
    private static final String FTP_PASSWORD = SystemConfig.get("system.ftp.password");     // FTP
    private static final String FTP_PORT = SystemConfig.get("system.ftp.port");


    /**
     * 私有的构造方法,不允许实例化
     */
    private FtpUtils() {}

    /**
     * 上传文件
     * 通过ftp协议
     * @param localPath 待上传文件的全路径
     * @param remotePath 远程目录, 即FTP服务器上的目录
     * @return
     */
    public static boolean upload(String localPath, String remotePath) {
        boolean result = false;
        if (StringUtils.isBlank(localPath)) {
            System.out.println("****** 上传参数localPath为空");
            return result;
        }
        if (StringUtils.isBlank(remotePath)) {
            System.out.println("****** 上传参数remotePath为空");
            return result;
        }

        File file = new File(localPath);                            // 本地文件
        if (!file.exists()) {
            System.out.println("****** 本地文件不存在, 请检查" + localPath);
            return result;
        }

        FTPClient client = new FTPClient();
        InputStream is = null;
        try {
            setClientProperty(client);                              // 设置FTPClient的参数
            int reply = client.getReplyCode();                      // 服务器回复代码
            System.out.println("****** FTP服务器回复代码: " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println("****** FTP服务器连接断开");
                client.disconnect();
                return result;
            }

            boolean changeDir = client.changeWorkingDirectory(remotePath);  // 切换到上传文件的目录,即主目录下的子目录
            if (!changeDir) {
                System.out.println("****** 切换FTP工作目录失败, 请检查\"" + remotePath + "\"是否在FTP主目录下, 或查看/etc/passwd下的用户主目录设置");
            }

            client.setControlEncoding("UTF-8");

            is = FileUtils.openInputStream(file);                   // 转换为流
            result = client.storeFile(file.getName(), is);          // 上传
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            closeClient(client);
        }
        return result;
    }

    /**
     * 下载文件
     * 通过ftp协议
     * @param remotePath 待下载的文件路径(远程目录, 即FTP服务器上的目录)
     * @param fileName 待下载的文件名
     * @param localPath 本地目录
     * @return
     */
    public static boolean download(String remotePath, String fileName, String localPath) {
        boolean result = false;
        if (StringUtils.isBlank(remotePath)) {
            System.out.println("****** 上传参数remotePath为空");
            return result;
        }
        if (StringUtils.isBlank(localPath)) {
            System.out.println("****** 上传参数localPath为空");
            return result;
        }

        OutputStream os = null;
        FTPClient client = new FTPClient();
        try {
            setClientProperty(client);                                      // 设置FTPClient的参数
            int reply = client.getReplyCode();                              // 服务器回复代码
            System.out.println("****** FTP服务器回复代码: " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                System.out.println("****** FTP服务器连接断开");
                client.disconnect();
                return result;
            }

            boolean changeDir = client.changeWorkingDirectory(remotePath);  // 切换到下载文件的目录,即主目录下的子目录
            if (!changeDir) {
                System.out.println(System.getProperty("user.home"));
                System.out.println("****** 切换FTP工作目录失败, 请检查\"" + remotePath + "\"是否在FTP主目录下, 或查看/etc/passwd下的用户主目录设置");
            }

            boolean match = Arrays.stream(client.listFiles()).anyMatch((e) -> StringUtils.equalsIgnoreCase(e.getName(), fileName));
            if (!match) {// 文件不存在
                System.out.println("****** " + fileName + "文件在FTP服务器的\"" + remotePath + "\"目录中不存在");
            } else {// 下载fileName
                File file = new File(localPath + File.separator + fileName);
                os = new FileOutputStream(file);
                result = client.retrieveFile(fileName, os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
            closeClient(client);
        }
        return result;
    }


    private static void setClientProperty(FTPClient client) throws IOException {
        Integer port = StringUtils.isBlank(FTP_PORT) ? FTPClient.DEFAULT_PORT : Integer.valueOf(FTP_PORT);
        client.connect(FTP_HOST, port);                         // 连接FTP服务器
        client.login(FTP_USERNAME, FTP_PASSWORD);               // 登录
        client.setFileType(FTP.BINARY_FILE_TYPE);               // 文件类型,二进制
        client.enterLocalPassiveMode();                         // 被动模式
        client.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);   // 文件传输模式
    }


    private static void closeClient(FTPClient client) {
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        String localPath = "/Users/ruanyh/Documents/temp.pdf";
        String remotePath = "local";
        FtpUtils.upload(localPath, remotePath);

//        String remotePath = "local";
//        String localPath = "/Users/ruanyh/Desktop";
//        System.out.println(FtpUtils.download(remotePath, "temp2.pdf", localPath));
    }

}
