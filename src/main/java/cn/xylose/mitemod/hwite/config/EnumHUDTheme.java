package cn.xylose.mitemod.hwite.config;

import fi.dy.masa.malilib.util.StringUtils;

public enum EnumHUDTheme {
    Waila("#CC10010F", "#CC5001FE", "#CC28017E"),
    Dark("#CC131313", "#CC383838", "#CC242424"),
    TOP("#CC006699", "#CC9999ff", "#CC9999ff"),
    Create("#CC000000", "#CC2A2626", "#CC1A1717"),
    ;

    public final int backgroundColor;
    public final int frameColorTop;
    public final int frameColorBottom;


    EnumHUDTheme(String backgroundColor, String frameColorTop, String frameColorBottom) {
        this.backgroundColor = StringUtils.getColor(backgroundColor, 0);
        this.frameColorTop = StringUtils.getColor(frameColorTop, 0);
        this.frameColorBottom = StringUtils.getColor(frameColorBottom, 0);
    }

    EnumHUDTheme(int BGColor, int frameColorTop, int frameColorBottom) {
        this.backgroundColor = BGColor;
        this.frameColorTop = frameColorTop;
        this.frameColorBottom = frameColorBottom;
    }
}
