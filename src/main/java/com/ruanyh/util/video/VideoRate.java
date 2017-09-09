package com.ruanyh.util.video;

/**
 * 视频帧率
 * Created by ruanyh on 17/9/9.
 */
public enum VideoRate {
    NTSC("ntsc", 30000 / 1001),
    PAL("pal", 25 / 1),
    QNTSC("qntsc", 30000 / 1001),
    QPAL("qpal", 25 / 1),
    SNTSC("sntsc", 30000 / 1001),
    SPAL("spal", 25 / 1),
    FILM("film", 24 / 1),
    NTSC_FILM("ntsc-film", 24000 / 1001);

    private String name;
    private Integer rate;

    VideoRate(String name, Integer rate) {
        this.name = name;
        this.rate = rate;
    }
}
