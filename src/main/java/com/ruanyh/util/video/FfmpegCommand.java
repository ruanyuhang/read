package com.ruanyh.util.video;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * ffmpeg命令
 * Created by ruanyh on 17/9/9.
 */
public class FfmpegCommand {
    private final String ffmpegPath = "ffmpeg";           // ffmpeg的安装位置

    private String inputPath;               // 输入路径
    private String outputPath;              // 输出目录

    private String watermarkPath;           // 水印路径
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
     * @param inputPath 输入路径
     * @return
     */
    public static FfmpegCommand.Builder of(String inputPath) {
        return new FfmpegCommand.Builder(inputPath);
    }

    /**
     * 创建构造者
     * @param inputPath 输入路径
     * @param outputPath 输出路径
     * @return
     */
    public static FfmpegCommand.Builder of(String inputPath, String outputPath) {
        return new FfmpegCommand.Builder(inputPath, outputPath);
    }

    /**
     * 创建构造者
     * @param inputFile 输入文件
     * @return
     */
    public static FfmpegCommand.Builder of(File inputFile) {
        return of(inputFile.getAbsolutePath());
    }

    /**
     * 创建构造者
     * @param inputFile 输入文件
     * @param outputFile 输出文件
     * @return
     */
    public static FfmpegCommand.Builder of(File inputFile, File outputFile) {
        return of(inputFile.getAbsolutePath(), outputFile.getAbsolutePath());
    }



    /**
     * 生成输出路径
     * @return
     */
    public String generateOutputPath() {
        return outputPath;
    }


    /**
     * 生成转码命令
     * @return
     */
    public String generateTranscodingCommand() {
        StringBuffer buffer = new StringBuffer(ffmpegPath);
        buffer.append(" -i ").append(inputPath);
        if (addWatermark) {// 需要加水印
            buffer.append(" -vf \"movie=" + watermarkPath + " [watermark]; [in][watermark] ")
                    .append("overlay=")
                    .append(overlayX != null ? overlayX : defaultOverlayX)
                    .append(":")
                    .append(overlayY != null ? overlayY : defaultOverlayY)
                    .append(" [out]\" ");
        }
        buffer.append(" -c:v libx264 -strict -2 ");
        buffer.append(outputPath);
        return buffer.toString();
    }

    /**
     * 生成图片转视频命令
     * @return
     */
    public String generateImage2VideoCommand() {
        StringBuffer buffer = new StringBuffer(ffmpegPath);
        buffer.append(" -r 25 -loop 1 -i ")
            .append(inputPath)  // 原文件的路径
            .append(" -r 25 -t 3 ")
            .append(outputPath); // 文件输出路径
        return buffer.toString();
    }


    @Override
    public String toString() {
        return "FfmpegCommand{" +
                "ffmpegPath='" + ffmpegPath + '\'' +
                ", inputPath='" + inputPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                ", watermarkPath='" + watermarkPath + '\'' +
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
         * @param inputPath
         */
        public Builder(String inputPath) {
            target = new FfmpegCommand();
            target.inputPath = inputPath;
        }

        public Builder(String inputPath, String outputPath) {
            target = new FfmpegCommand();
            target.inputPath = inputPath;
            target.outputPath = outputPath;
        }

        /**
         * 添加水印
         * @param watermarkPath 水印位置
         * @return
         */
        public Builder addWatermark(String watermarkPath) {
            if (StringUtils.isBlank(watermarkPath)) {
                throw new RuntimeException("[设置水印] 参数watermarkPath不能为空");
            }
            target.watermarkPath = watermarkPath;
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
         * 输出到outputPath
         * @param outputPath 输出路径
         * @return
         */
        public Builder outputTo(String outputPath) {
            if (outputPath == null) {
                throw new RuntimeException("[设置输出路径] 参数outputPath不能为空");
            }
            target.outputPath = outputPath;
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
        String outoutPath = "/User/ruanyh/Document/out.mp4";
        FfmpegCommand command = FfmpegCommand.of(inputPath).addWatermark(watermarkPath).setOverlayX(0).setOverlayY(0).outputTo(outoutPath).build();
        System.out.println(command);
        System.out.println(command.generateTranscodingCommand());
    }


}
