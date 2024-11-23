package mcp.mobius.waila.overlay;

import moddedmite.waila.config.WailaConfig;

public class OverlayConfig {

    public static int posX;
    public static int posY;
    public static int alpha;
    public static int bgcolor;
    public static int gradient1;
    public static int gradient2;
    public static int fontcolor;
    public static float scale = WailaConfig.scale.getIntegerValue();

    public static void updateColors() {
        OverlayConfig.alpha = (int) (WailaConfig.alpha.getIntegerValue() / 100.0f * 256) << 24;
        OverlayConfig.bgcolor = OverlayConfig.alpha + WailaConfig.bgcolor.getColorInteger();
        OverlayConfig.gradient1 = OverlayConfig.alpha + WailaConfig.gradient1.getColorInteger();
        OverlayConfig.gradient2 = OverlayConfig.alpha + WailaConfig.gradient2.getColorInteger();
        OverlayConfig.fontcolor = OverlayConfig.alpha + WailaConfig.fontcolor.getColorInteger();
    }
}
