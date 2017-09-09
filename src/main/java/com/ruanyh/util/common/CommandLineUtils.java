package com.ruanyh.util.common;

import java.io.*;

/**
 * 命令行工具
 */
public class CommandLineUtils {
    private static String cmd = System.getProperty("os.name").toLowerCase().startsWith("windows") ? "cmd" : "sh";
    private static String param = System.getProperty("os.name").toLowerCase().startsWith("windows") ? "/c" : "-c";

    /**
     * 私有的构造方法,不允许实例化
     */
    private CommandLineUtils() {}


    /**
     * 执行命令
     * @param command 命令
     * @return
     */
    public static String exec(String command) {
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{cmd, param, command});

            Worker worker = new Worker(process);
            worker.start();
            try {
                int timeout = 0;
                if (timeout == 0) {
                    timeout = 180 * 1000;
                }
                worker.join(timeout);
                if (worker.exit == null) {
                    System.out.println("ExecTimeOut:" + command.toString());
                }
                return worker.returnStr;
            } catch (InterruptedException ex) {
                worker.interrupt();
                Thread.currentThread().interrupt();
                throw ex;
            } finally {
                process.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    private static class Worker extends Thread {
        private final Process process;
        private Integer exit;
        private String returnStr;
        private StringBuffer errStr;
        private StringBuffer normalStr;

        private Worker(Process process) {
            this.process = process;
        }

        public void run() {
            try {
                exit = doWaitFor(process);
                returnStr = errStr.toString() + normalStr.toString();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                return;
            } finally {
                process.destroy();
            }
        }

        public int doWaitFor(Process process) {
            InputStream in = null;
            InputStream err = null;
            errStr = new StringBuffer();
            normalStr = new StringBuffer();
            int exitValue = -1; // returned to caller when p is finished
            try {
                in = process.getInputStream();
                err = process.getErrorStream();
                boolean finished = false; // Set to true when p is finished
                while (!finished) {
                    try {
                        while (in.available() > 0) {
                            // Print the output of our system call
                            Character c = new Character((char) in.read());
                            errStr.append(c);
                        }
                        while (err.available() > 0) {
                            // Print the output of our system call
                            Character c = new Character((char) err.read());
                            normalStr.append(c);
                        }
                        // Ask the process for its exitValue. If the process
                        // is not finished, an IllegalThreadStateException
                        // is thrown. If it is finished, we fall through and
                        // the variable finished is set to true.
                        exitValue = process.exitValue();
                        finished = true;
                    } catch (IllegalThreadStateException e) {
                        // Process is not finished yet;
                        // Sleep a little to save on CPU cycles
                        Thread.currentThread().sleep(500);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (err != null) {
                    try {
                        err.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return exitValue;
        }
    }

}
