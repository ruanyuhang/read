package com.ruanyh.util.video;

/**
 * 视频大小
 * Created by ruanyh on 17/9/9.
 */
public enum VideoSize {
    NTSC("ntsc", "720x480"),
    PAL("pal", "720x576"),
    QNTSC("qntsc", "352x240"),
    QPAL("qpal", "352x288"),
    SNTSC("sntsc", "640x480"),
    SPAL("spal", "768x576"),
    FILM("film", "352x240"),
    NTSC_FILM("ntsc-film", "352x240"),
    SQCIF("sqcif", "128x96"),
    QCIF("qcif", "176x144"),
    CIF("cif", "352x288"),

    FOUR_CIF("4cif", "704x576"),
    SIXTEEN_CIF("16cif", "1408x1152"),

    QQVGA("qqvga", "160x120"),
    QVGA("qvga", "320x240"),
    VGA("vga", "640x480"),
    SVGA("svga", "800x600"),
    XGA("xga", "1024x768"),
    UXGA("uxga", "1600x1200"),
    QXGA("qxga", "2048x1536"),
    SXGA("sxga", "1280x1024"),
    QSXGA("qsxga", "2560x2048"),
    HSXGA("hsxga", "5120x4096"),
    WVGA("wvga", "852x480"),
    WXGA("wxga", "1366x768"),
    WSXGA("wsxga", "1600x1024"),
    WUXGA("wuxga", "1920x1200"),
    WOXGA("woxga", "2560x1600"),
    WQSXGA("wqsxga", "3200x2048"),
    WQUXGA("wquxga", "3840x2400"),
    WHSXGA("whsxga", "6400x4096"),
    WHUXGA("whuxga", "7680x4800"),
    CGA("cga", "320x200"),
    EGA("ega", "640x350"),

    HD480("hd480", "852x480"),
    HD720("hd720", "1280x720"),
    HD1080("hd1080", "1920x1080"),

    SECOND_K("2k", "2048x1080"),
    SECOND_KFLAT("2kflat", "1998x1080"),
    SECOND_KSCOPE("2kscope", "2048x858"),

    FOUR_K("4k", "4096x2160"),
    FOUR_KFLAT("4kflat", "3996x2160"),
    FOUR_KSCOPE("4kscope", "4096x1716"),
    NHD("nhd", "640x360"),
    HQVGA("hqvga", "240x160"),
    WQVGA("wqvga", "400x240"),
    FWQVGA("fwqvga", "432x240"),
    HVGA("hvga", "480x320"),
    QHD("qhd", "960x540"),
    SECOND_KDCI("2kdci", "2048x1080"),
    FOUR_KDCI("4kdci", "4096x2160"),
    UHD2160("uhd2160", "3840x2160"),
    UHD4320("uhd4320", "7680x4320");

    private String name;
    private String size;

    VideoSize(String name, String size) {
        this.name = name;
        this.size = size;
    }
}
