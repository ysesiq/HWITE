package cn.xylose.mitemod.hwite.render;

import cn.xylose.mitemod.hwite.config.EnumHUDTheme;
import cn.xylose.mitemod.hwite.config.HwiteConfigs;
import cn.xylose.mitemod.hwite.info.HwiteInfo;
import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import cn.xylose.mitemod.hwite.util.DisplayUtil;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static cn.xylose.mitemod.hwite.api.SpecialChars.patternTab;
import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.render.Tooltip.TabSpacing;

public class HUDBackGroundRender extends Gui {

    public static int x;
    public static int y;
    public static int w;
    public static int h;
    public static final RenderItem itemRenderer = new RenderItem();

    public HUDBackGroundRender() {
    }

    public void drawTooltipBackGround(List<String> par1List, boolean has_title, Minecraft mc) {
        if (par1List == null || par1List.isEmpty()) {
            return;
        }
        FontRenderer fontRenderer = mc.fontRenderer;
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        int var4 = 0;

        int maxStringW = 0;

        for (String s : par1List) {
            ArrayList<String> line = new ArrayList<>(Arrays.asList(patternTab.split(s)));
            y = fontRenderer.getStringWidth(s);

            if (y > var4) {
                var4 = y;
            }
            maxStringW = Math.max(maxStringW, DisplayUtil.getDisplayWidth(s) + TabSpacing * (line.size() - 1));
        }

        int var8 = 8;

        if (par1List.size() > 1) {
            var8 += 2 + (par1List.size() - 1) * 10;
        }

        if (!has_title) {
            var8 -= 2;
        }

        Point pos = new Point(HUDX.getIntegerValue(), HUDY.getIntegerValue());

        int paddingW = HwiteInfo.hasIcon ? 29 : 13;
        int paddingH = HwiteInfo.hasIcon ? 24 : 0;
        int offsetX = HwiteInfo.hasIcon ? 24 : 6;

        w = maxStringW + paddingW;
        h = par1List.size() * 11 + 3;

        Dimension size = DisplayUtil.displaySize();
        x = ((int) (size.width / (float) HUDScale.getDoubleValue()) - w - 1) * pos.x / 100;
        y = ((int) (size.height / (float) HUDScale.getDoubleValue()) - h - 1) * pos.y / 100;

        if (y + var8 + 6 > height) {
            y = height - var8 - 6;
        }

        itemRenderer.zLevel = 300.0F;
        Color4f tooltipBGColor;
        Color4f tooltipFrameColorTop;
        Color4f tooltipFrameColorBottom;
        float alpha = (float) HUDAlpha.getIntegerValue() / 100;
        if (HUDThemeSwitch.getBooleanValue()) {
            EnumHUDTheme theme = HUDTheme.getEnumValue();
            tooltipBGColor = Color4f.fromColor(theme.backgroundColor, alpha);
            tooltipFrameColorTop = Color4f.fromColor(theme.frameColorTop, alpha);
            tooltipFrameColorBottom = Color4f.fromColor(theme.frameColorBottom, alpha);
        } else {
            tooltipBGColor = Color4f.fromColor(HUDBGColor.getColorInteger(), alpha);
            tooltipFrameColorTop = Color4f.fromColor(HUDFrameColor.getColorInteger(), alpha);
            tooltipFrameColorBottom = Color4f.fromColor(HUDFrameColor1.getColorInteger(), alpha);
        }

        if (HUDBackGround.getBooleanValue()) {
//            if (HUDCentralBackground.getBooleanValue()) {
//                //中
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth + var4 + 3, stringHeight + var8 + 5, tooltipBGColor, tooltipBGColor, zLevel);
//            }
//            if (HUDRoundedRectangle.getBooleanValue()) {
//                //上
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 6, stringWidth + var4 + 3, stringHeight - 5, tooltipBGColor, tooltipBGColor, zLevel);
//                //下
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 5, stringWidth + var4 + 3, stringHeight + var8 + 6, tooltipBGColor, tooltipBGColor, zLevel);
//                //左
//                drawGradientRect(stringWidth - 4 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 5, tooltipBGColor, tooltipBGColor, zLevel);
//                //右
//                drawGradientRect(stringWidth + var4 + 3, stringHeight - 5, stringWidth + var4 + 4, stringHeight + var8 + 5, tooltipBGColor, tooltipBGColor, zLevel);
//            }
//            if (HUDFrame.getBooleanValue()) {
//                //上
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth + var4 + 3, stringHeight - 5 + 1, tooltipFrameColorTop, tooltipFrameColorTop, zLevel);
//                //下
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 4, stringWidth + var4 + 3, stringHeight + var8 + 5, tooltipFrameColorBottom, tooltipFrameColorBottom, zLevel);
//                //左
//                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5 + 1, stringWidth - 3 + 1 - BlockRenderLeftEliminate, stringHeight + var8 + 5 - 1, tooltipFrameColorTop, tooltipFrameColorBottom, zLevel);
//                //右
//                drawGradientRect(stringWidth + var4 + 2, stringHeight - 5 + 1, stringWidth + var4 + 3, stringHeight + var8 + 5 - 1, tooltipFrameColorTop, tooltipFrameColorBottom, zLevel);
//            }
            if (HUDRoundedRectangle.getBooleanValue() && !(HUDTheme.getEnumValue() == EnumHUDTheme.TOP)) {
                DisplayUtil.drawGradientRect(x + 1, y, w - 1, 1, tooltipBGColor.intValue, tooltipBGColor.intValue);
                DisplayUtil.drawGradientRect(x + 1, y + h, w - 1, 1, tooltipBGColor.intValue, tooltipBGColor.intValue);
                DisplayUtil.drawGradientRect(x, y + 1, 1, h - 1, tooltipBGColor.intValue, tooltipBGColor.intValue);
                DisplayUtil.drawGradientRect(x + w, y + 1, 1, h - 1, tooltipBGColor.intValue, tooltipBGColor.intValue);
            }

            if (HUDCentralBackground.getBooleanValue())
                DisplayUtil.drawGradientRect(x + 1, y + 1, w - 1, h - 1, tooltipBGColor.intValue, tooltipBGColor.intValue);// center

            if (HUDFrame.getBooleanValue()) {
                DisplayUtil.drawGradientRect(x + 1, y + 2, 1, h - 3, tooltipFrameColorTop.intValue, tooltipFrameColorBottom.intValue);
                DisplayUtil.drawGradientRect(x + w - 1, y + 2, 1, h - 3, tooltipFrameColorTop.intValue, tooltipFrameColorBottom.intValue);
                DisplayUtil.drawGradientRect(x + 1, y + 1, w - 1, 1, tooltipFrameColorTop.intValue, tooltipFrameColorTop.intValue);
                DisplayUtil.drawGradientRect(x + 1, y + h - 1, w - 1, 1, tooltipFrameColorBottom.intValue, tooltipFrameColorBottom.intValue);
            }
        }


        float breakProgress = ((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress();
        if (breakProgress > 0 && BreakProgressLine.getBooleanValue()) {
            int progress = (int) (breakProgress * 100F);
            DisplayUtil.drawGradientRect(progress + x + 1, y + h,w - 1, 1, BreakProgressLineColor.getColorInteger(), BreakProgressLineColor.getColorInteger());
       }
        for (int var12 = 0; var12 < par1List.size(); ++var12) {
            String var13 = par1List.get(var12);
            fontRenderer.drawStringWithShadow(var13, HwiteInfo.hasIcon ? x + 25 : x + 9, y + 4, -1);

            if (var12 == 0 && has_title) {
                y += 2;
            }

            y += 10;
        }

        zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

}
