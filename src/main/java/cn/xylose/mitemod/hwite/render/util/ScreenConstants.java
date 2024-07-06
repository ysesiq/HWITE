package cn.xylose.mitemod.hwite.render.util;

import net.minecraft.BossStatus;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.config.HwiteConfigs.HUDY;

public class ScreenConstants {
    public static int getHudY() {
        if (BossStatus.bossName == null) {
            if (HUDPosOverride.getBooleanValue()) {
                return HUDY.getIntegerValue();
            } else {
                return 16;
            }
//        } else if (BossStatus.bossName != null) {
//            if (HUDPosOverride.getBooleanValue()) {
//                return HUDY.getIntegerValue() + 18;
//            } else {
//                return 36;
//            }
        }
        return 18;
    }

    public static int getBlockInfoX(int screenWidth) {
        int temp;
            if (HUDPosOverride.getBooleanValue()) {
                temp = HUDX.getIntegerValue() - 7;
            } else {
                temp = (screenWidth / 2) - 32;
                return temp;
            }
            return temp;
    }

    public static int getBlockInfoYSmall() {
        if (HUDPosOverride.getBooleanValue()) {
            int temp = HUDY.getIntegerValue();
            if (MITEDetailsInfo.getBooleanValue()) {
                return temp - 8;
            } else {
                return temp - 10;
            }
        } else {
            if (MITEDetailsInfo.getBooleanValue()) {
                return 8;
            } else {
                return 12;
            }
        }
    }

    public static int getHudX(int screenWidth) {
        if (HUDPosOverride.getBooleanValue()) {
            int temp = HUDX.getIntegerValue();
//            if (MITEDetailsInfo.getBooleanValue()) {
//                temp += 25;
//            }
            return temp;
        } else {
            int temp = (screenWidth / 2) - 25;
//            if (MITEDetailsInfo.getBooleanValue()) {
//                temp += 25;
//            }
            return temp;
        }
    }

    public static int getBlockInfoYBig() {
        if (HUDPosOverride.getBooleanValue()) {
            return HUDY.getIntegerValue();
        } else {
            return 12;
        }
    }
}
