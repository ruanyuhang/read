package com.ruanyh.util.video;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * ffmpeg命令
 * Created by ruanyh on 17/9/9.
 */
public class FfmpegCommand {
    private final String ffmpegPath = "ffmpeg";           // ffmpeg的安装位置

    private String inputFile;               // 输入文件
    private String outputFile;              // 输出文件

    private String watermark;           // 水印路径
    private boolean addWatermark;           // 需要加水印
    private Integer overlayX;               // 图片位置X轴
    private Integer overlayY;               // 图片位置Y轴

    private Integer fps;                    // 每秒帧数
    private String size;                    // 视频大小
    private String duration;                // 视频时长

    private final Integer defaultFps = 25;
    private final Integer defaultOverlayX = 10;   // 默认图片位置X轴
    private final Integer defaultOverlayY = 10;   // 默认图片位置Y轴

    /**
     * 创建构造者
     * @param inputFile 输入文件
     * @return
     */
    public static FfmpegCommand.Builder of(String inputFile) {
        return new FfmpegCommand.Builder(inputFile);
    }

    /**
     * 创建构造者
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @return
     */
    public static FfmpegCommand.Builder of(String inputFile, String outputFile) {
        return new FfmpegCommand.Builder(inputFile, outputFile);
    }

    /**
     * 创建构造者
     * @param originalFile 输入文件
     * @return
     */
    public static FfmpegCommand.Builder of(File originalFile) {
        return of(originalFile.getAbsolutePath());
    }

    /**
     * 创建构造者
     * @param originalFile 输入文件
     * @param targetFile 输出文件
     * @return
     */
    public static FfmpegCommand.Builder of(File originalFile, File targetFile) {
        return of(originalFile.getAbsolutePath(), targetFile.getAbsolutePath());
    }



    /**
     * 生成输出文件的绝对路径
     * @return
     */
    public String generateOutputAbsolutePath() {
        return outputFile;
    }


    /**
     * 生成转码命令
     * @return
     */
    public String generateTranscodingCommand() {
        StringBuffer buffer = new StringBuffer(ffmpegPath);
        buffer.append(" -i ").append(inputFile);
        if (addWatermark) {// 需要加水印
            buffer.append(" -vf \"movie=" + watermark + " [watermark]; [in][watermark] ")
                    .append("overlay=")
                    .append(overlayX != null ? overlayX : defaultOverlayX)
                    .append(":")
                    .append(overlayY != null ? overlayY : defaultOverlayY)
                    .append(" [out]\" ");
        }
        buffer.append(" -c:v libx264 -strict -2 ");
        buffer.append(outputFile);
        return buffer.toString();
    }

    /**
     * 生成图片转视频命令
     * @return
     */
    public String generateImage2VideoCommand() {
        StringBuffer buffer = new StringBuffer(ffmpegPath);
        buffer.append(" -r 25 -loop 1 -i ")
            .append(inputFile)  // 原文件的路径
            .append(" -r 25 -t 3 ")
            .append(outputFile); // 文件输出路径
        return buffer.toString();
    }


    @Override
    public String toString() {
        return "FfmpegCommand{" +
                "ffmpegPath='" + ffmpegPath + '\'' +
                ", inputPath='" + inputFile + '\'' +
                ", outputPath='" + outputFile + '\'' +
                ", watermark='" + watermark + '\'' +
                ", addWatermark=" + addWatermark +
                ", overlayX=" + overlayX +
                ", overlayY=" + overlayY +
                ", fps=" + fps +
                ", size='" + size + '\'' +
                ", duration='" + duration + '\'' +
                ", defaultFps=" + defaultFps +
                ", defaultOverlayX=" + defaultOverlayX +
                ", defaultOverlayY=" + defaultOverlayY +
                '}';
    }



    /**
     * ffmpeg命令构造
     */
    public static class Builder {
        private FfmpegCommand target;

        /**
         * 构造
         * @param inputFile
         */
        public Builder(String inputFile) {
            target = new FfmpegCommand();
            target.inputFile = inputFile;
        }

        /**
         * 构造
         * @param inputFile
         * @param outputFile
         */
        public Builder(String inputFile, String outputFile) {
            target = new FfmpegCommand();
            target.inputFile = inputFile;
            target.outputFile = outputFile;
        }


        /**
         * 添加水印
         * @param watermark 水印位置
         * @return
         */
        public Builder addWatermark(String watermark) {
            if (StringUtils.isBlank(watermark)) {
                throw new RuntimeException("[设置水印] 参数watermark不能为空");
            }
            target.watermark = watermark;
            target.addWatermark = true;
            return this;
        }

        /**
         * 设置图片位置:x轴
         * @param overlayX x轴
         * @return
         */
        public Builder setOverlayX(Integer overlayX) {
            if (overlayX == null) {
                System.out.println("");
            }
            target.overlayX = overlayX;
            return this;
        }

        /**
         * 设置图片位置:y轴
         * @param overlayY y轴
         * @return
         */
        public Builder setOverlayY(Integer overlayY) {
            if (overlayY == null) {
                System.out.println("");
            }
            target.overlayY = overlayY;
            return this;
        }

        /**
         * 设置图片位置
         * @param overlayX
         * @param overlayY
         * @return
         */
        public Builder setOverlayPosition(Integer overlayX, Integer overlayY) {
            if (overlayX == null) {
                System.out.println("");
            }
            target.overlayX = overlayX;
            target.overlayY = overlayY;
            return this;
        }

        /**
         * 设置视频时长
         * The following examples are all valid time duration:
         * ‘55’
         * 55 seconds
         * ‘12:03:45’
         * 12 hours, 03 minutes and 45 seconds
         * ‘23.189’
         * 23.189 seconds
         * @param duration
         * @return
         */
        public Builder setVideoDuration(String duration) {
            if (StringUtils.isBlank(duration)) {
                throw new RuntimeException("[设置视频时长] 参数duration不能为空");
            }
            target.duration = duration;
            return this;
        }

        /**
         * 设置视频大小
         * 参考VideoSize类
         * @param size
         * @return
         */
        public Builder setVideoSize(String size) {
            if (StringUtils.isBlank(size)) {
                throw new RuntimeException("[设置视频大小] 参数size不能为空");
            }
            target.size = size;
            return this;
        }

        /**
         * 设置视频帧率
         * @param fps
         * @return
         */
        public Builder setVideoRate(Integer fps) {
            if (fps == null) {
                throw new RuntimeException("[设置视频帧率] 参数fps不能为空");
            }
            target.fps = fps;
            return this;
        }



        /**
         * 输出到outputFile
         * @param outputFile 输出路径
         * @return
         */
        public Builder outputFile(String outputFile) {
            if (outputFile == null) {
                throw new RuntimeException("[设置输出路径] 参数outputFile不能为空");
            }
            target.outputFile = outputFile;
            return this;
        }



        /**
         * 构造ffmpeg命令对象
         * @return
         */
        public FfmpegCommand build() {
            return target;
        }

    }



    public static void main(String[] args) {
        String inputPath = "/User/ruanyh/Documents/input.mp4";
        String watermarkPath = "/User/ruanyh/Documents/watermark.png";
        String outputPath = "/User/ruanyh/Document/out.mp4";
        FfmpegCommand command = FfmpegCommand.of(inputPath).addWatermark(watermarkPath).setOverlayX(0).setOverlayY(0).outputFile(outputPath).build();
        System.out.println(command);
        System.out.println(command.generateTranscodingCommand());

        String os = System.getProperty("os.name");
        System.out.println(os);
    }


}
