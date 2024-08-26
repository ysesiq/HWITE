package moddedmite.xylose.hwite.config;

import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;

public enum EnumHUDTheme {
    Waila("#10010F", "#5001FE", "#28017E"),
    Dark("#131313", "#383838", "#242424"),
    TOP("#006699", "#9999ff", "#9999ff"),
    Create("#000000", "#2A2626", "#1A1717"),
    Tooltip("#130211", "#1F0639", "#160321"),
    Achievement("#212121", "#555555", "#555555");

    public final Color4f backgroundColor;
    public final Color4f frameColorTop;
    public final Color4f frameColorBottom;

    EnumHUDTheme(String backgroundColor, String frameColorTop, String frameColorBottom) {
        this.backgroundColor = Color4f.fromColor(StringUtils.getColor(backgroundColor, 0));
        this.frameColorTop = Color4f.fromColor(StringUtils.getColor(frameColorTop, 0));
        this.frameColorBottom = Color4f.fromColor(StringUtils.getColor(frameColorBottom, 0));
    }

    EnumHUDTheme(int BGColor, int frameColorTop, int frameColorBottom) {
        this.backgroundColor = Color4f.fromColor(BGColor);
        this.frameColorTop = Color4f.fromColor(frameColorTop);
        this.frameColorBottom = Color4f.fromColor(frameColorBottom);
    }
}
