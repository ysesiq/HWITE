package cn.xylose.mitemod.hwite.render;

import static cn.xylose.mitemod.hwite.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.HwiteConfigs.HUDY;

public class ScreenConstants {
    public static int getHudY() {
        if (HUDPosOverride.getBooleanValue()) {
            return HUDY.getIntegerValue();
        } else {
            return 18;
        }
    }

    public static int getBlockInfoX(int screenWidth) {
        if (HUDPosOverride.getBooleanValue()) {
            if (shiftMoreInfo.getBooleanValue()) {
                return HUDX.getIntegerValue() + 18;
            } else {
                return HUDX.getIntegerValue() - 7;
            }
        } else {
            int temp = screenWidth / 2 - 57;
            if (shiftMoreInfo.getBooleanValue()) {
                temp += 25;
            }
            return temp;
        }
    }

    public static int getBlockInfoYSmall() {
        if (HUDPosOverride.getBooleanValue()) {
            int temp = HUDY.getIntegerValue();
            if (shiftMoreInfo.getBooleanValue()) {
                return temp - 10;
            } else {
                return temp - 6;
            }
        } else {
            if (shiftMoreInfo.getBooleanValue()) {
                return 8;
            } else {
                return 12;
            }
        }
    }

    public static int getHudX(int screenWidth) {
        if (HUDPosOverride.getBooleanValue()) {
            int temp = HUDX.getIntegerValue();
            if (shiftMoreInfo.getBooleanValue()) {
                temp += 25;
            }
            return temp;
        } else {
            int temp = screenWidth / 2 - 50;
            if (shiftMoreInfo.getBooleanValue()) {
                temp += 25;
            }
            return temp;
        }
    }

    public static int getBlockInfoYBig() {
        if (HUDPosOverride.getBooleanValue()) {
            return HUDY.getIntegerValue() - 2;
        } else {
            return 12;
        }
    }
}
