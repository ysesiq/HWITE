package cn.xylose.mitemod.hwite.render;

import cn.xylose.mitemod.hwite.config.EnumHUDTheme;
import cn.xylose.mitemod.hwite.info.HwiteInfo;
import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;
import java.util.Objects;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;

public class HUDBackGroundRender extends Gui {

    public static int stringWidth;
    public static int stringHeight;
    public static int BlockRenderLeftEliminate;
    public static final RenderItem itemRenderer = new RenderItem();

    public int getStringWidth() {
        return stringWidth;
    }

    public int getStringHeight() {
        return stringHeight;
    }

    public static void drawTooltipBackGround(List<String> par1List, int par2, int par3, boolean has_title, Minecraft mc, double zLevel) {
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

        for (String s : par1List) {
            stringHeight = fontRenderer.getStringWidth(s);

            if (stringHeight > var4) {
                var4 = stringHeight;
            }
        }

        stringWidth = par2 + 12;
        stringHeight = par3 - 12;
        int var8 = 8;

        if (par1List.size() > 1) {
            var8 += 2 + (par1List.size() - 1) * 10;
        }

        if (!has_title) {
            var8 -= 2;
        }

        if (stringWidth + var4 > width) {
            stringWidth -= 28 + var4;
        }

        if (stringHeight + var8 + 6 > height) {
            stringHeight = height - var8 - 6;
        }

        BlockRenderLeftEliminate = 0;
        if (HwiteInfo.entityInfo == null) {
//            if (MITEDetailsInfo.getBooleanValue()) {
            if (!Objects.equals(HwiteInfo.infoMain, "") && !Objects.equals(HwiteInfo.break_info, "  ") && BlockRender.getBooleanValue()) {
                BlockRenderLeftEliminate += 20;
            } else if (!Objects.equals(HwiteInfo.break_info, " ") && BlockRender.getBooleanValue()) {
                BlockRenderLeftEliminate += 20;
            }
//            } else {
//                if (!Objects.equals(HwiteInfo.infoMain, "") && !Objects.equals(HwiteInfo.break_info, "") && BlockRender.getBooleanValue()) {
//                    BlockRenderLeftEliminate += 20;
//                } else if (!Objects.equals(HwiteInfo.info_line_2, "") && !Objects.equals(HwiteInfo.break_info, "") && BlockRender.getBooleanValue()) {
//                    BlockRenderLeftEliminate += 20;
//                }
//            }
        }
        itemRenderer.zLevel = 300.0F;
        int hudBGColor1;
        int hudFrameColorTop1;
        int hudFrameColorBottom1;
        if (HUDThemeSwitch.getBooleanValue()) {
            EnumHUDTheme theme = HUDTheme.getEnumValue();
            hudBGColor1 = theme.backgroundColor;
            hudFrameColorTop1 = theme.frameColorTop;
            hudFrameColorBottom1 = theme.frameColorBottom;
        } else {
            hudBGColor1 = HUDBGColor.getColorInteger();
            hudFrameColorTop1 = HUDFrameColor.getColorInteger();
            hudFrameColorBottom1 = HUDFrameColor1.getColorInteger();
        }

        if (HUDBackGround.getBooleanValue()) {
            if (HUDCentralBackground.getBooleanValue()) {
                //中
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth + var4 + 3, stringHeight + var8 + 5, hudBGColor1, hudBGColor1, zLevel);
            }
            if (HUDRoundedRectangle.getBooleanValue()) {
                //上
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 6, stringWidth + var4 + 3, stringHeight - 5, hudBGColor1, hudBGColor1, zLevel);
                //下
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 5, stringWidth + var4 + 3, stringHeight + var8 + 6, hudBGColor1, hudBGColor1, zLevel);
                //左
                drawGradientRect(stringWidth - 4 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 5, hudBGColor1, hudBGColor1, zLevel);
                //右
                drawGradientRect(stringWidth + var4 + 3, stringHeight - 5, stringWidth + var4 + 4, stringHeight + var8 + 5, hudBGColor1, hudBGColor1, zLevel);
            }
            if (HUDFrame.getBooleanValue()) {
                //上
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5, stringWidth + var4 + 3, stringHeight - 5 + 1, hudFrameColorTop1, hudFrameColorTop1, zLevel);
                //下
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight + var8 + 4, stringWidth + var4 + 3, stringHeight + var8 + 5, hudFrameColorBottom1, hudFrameColorBottom1, zLevel);
                //左
                drawGradientRect(stringWidth - 3 - BlockRenderLeftEliminate, stringHeight - 5 + 1, stringWidth - 3 + 1 - BlockRenderLeftEliminate, stringHeight + var8 + 5 - 1, hudFrameColorTop1, hudFrameColorBottom1, zLevel);
                //右
                drawGradientRect(stringWidth + var4 + 2, stringHeight - 5 + 1, stringWidth + var4 + 3, stringHeight + var8 + 5 - 1, hudFrameColorTop1, hudFrameColorBottom1, zLevel);
            }
        }


        float breakProgress = ((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress();
        if (breakProgress > 0 && BreakProgressLine.getBooleanValue()) {
            int progress = (int) (breakProgress * 100F);
//                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, stringWidth1 + var4 + 3, stringWidth + var8 + 4, var9, var9, zLevel);
            drawGradientRect(
                    progress + stringWidth - 3 - BlockRenderLeftEliminate,
                    stringHeight + var8 + 3,
                    stringWidth + var4 + 3,
                    stringHeight + var8 + 4,
                    BreakProgressLineColor.getColorInteger(), BreakProgressLineColor.getColorInteger(), zLevel);
//
//                drawHorizontalLineHwite(var15 + 10, var15 + 110, lineHeight, 0xFFFFFFFF);
//                drawHorizontalLineHwite(var15 + 10, progress + var15 + 10, lineHeight, 0xFF000000);
//                drawHorizontalLineHwite(stringWidth1 + var4 + 3, progress + stringWidth1 - BlockRenderLeftEliminate, stringWidth + var8 + 3, 0xFFFFFFFF);
        }
        for (int var12 = 0; var12 < par1List.size(); ++var12) {
            String var13 = par1List.get(var12);
            fontRenderer.drawStringWithShadow(var13, stringWidth, stringHeight, -1);

            if (var12 == 0 && has_title) {
                stringHeight += 2;
            }

            stringHeight += 10;
        }

//            zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    private static void drawHorizontalLineHwite(int par1, int par2, int par3, int par4) {
        if (par2 < par1) {
            int var5 = par1;
            par1 = par2;
            par2 = var5;
        }
        Gui.drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }

    public static void drawGradientRect(int left, int top, int width, int height, int startColor, int endColor, double zLevel) {
        float var7 = (float) (startColor >> 24 & 255) / 255.0F;
        float var8 = (float) (startColor >> 16 & 255) / 255.0F;
        float var9 = (float) (startColor >> 8 & 255) / 255.0F;
        float var10 = (float) (startColor & 255) / 255.0F;
        float var11 = (float) (endColor >> 24 & 255) / 255.0F;
        float var12 = (float) (endColor >> 16 & 255) / 255.0F;
        float var13 = (float) (endColor >> 8 & 255) / 255.0F;
        float var14 = (float) (endColor & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(width, top, zLevel);
        var15.addVertex(left, top, zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(left, height, zLevel);
        var15.addVertex(width, height, zLevel);
        var15.draw();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    //已弃用
//    @Unique
//    private void renderBox(int mouseX, int mouseY) {
//        //绘制一个以屏幕左上角为零点的方形,通过剔除实现更丰富的形状
//
//        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
//        int scaledWidth = scaledResolution.getScaledWidth();
//        int scaledHeight = scaledResolution.getScaledHeight();
//
//        int length;
//        int width = BGWidth.get();
////        int width = scaledWidth / 2;
//        int height = BGHeight.get();
//        int leftEliminate = BGLeftEliminate.get();
//        int topEliminate = BGTopEliminate.get();
//
//        length = Math.max(info.length(), Math.max(info_line_1.length(), info_line_2.length()));
////        if (ModernMiteConfig.ASCIIFont.getBooleanValue()) {
////            length *= 1.5;
////        }
//
//        if (Objects.equals(info_line_2, "") && !Objects.equals(info, "")) {
//            height -= 10;
//        } else if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
//            height -= 20;
//        }
//
//        if (!Objects.equals(info, "") && entityInfo != null) {
//            leftEliminate -= 20;
////            width += 2;
//            height = 45;
//        }
//
//        if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
//            leftEliminate += 18;
//        }
//
//        if (BlockRender.get() && Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "") && Objects.equals(entityInfo, null)) {
//            leftEliminate += 20;
//        } else if ((BlockRender.get() && Objects.equals(info_line_2, "") && !Objects.equals(info, "") && Objects.equals(entityInfo, null))) {
//            leftEliminate += 20;
//        } else if ((BlockRender.get() && !Objects.equals(info_line_2, "") && !Objects.equals(info, "") && Objects.equals(entityInfo, null))) {
//            leftEliminate += 20;
//        } else if (EntityRender.get() && !Objects.equals(info, "") && entityInfo != null) {
//            leftEliminate += 40;
//            height -= 10;
//        }
//
//        if (!BlockRender.get()) {
//            leftEliminate += 20;
//            width += 20;
//        } else if (!EntityRender.get() && entityInfo != null) {
//            leftEliminate += 35;
//            width += 35;
//        }
//
//        if (!GuiScreen.isShiftKeyDown() && BlockRender.get() && !(break_info == " ")) {
//            height -= 10;
//        }
//
//        //边缘(底层)颜色
//        int var9 = -267386864;
//        var9 = var9 & 16777215 | -369098752;
//        //绘制边缘(底层)
//        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
//        //五行代码代表中间跟四个方向
//        //透明度:最深;左
//        this.drawGradientRect(leftEliminate, height, leftEliminate - 1, topEliminate, var9, var9);
//        //透明度:较浅;右
//        this.drawGradientRect(width + 1 + length, height, width + length, topEliminate, var9, var9);
//        //透明度:深;上
//        this.drawGradientRect(width + length, topEliminate, leftEliminate, topEliminate - 1, var9, var9);
//        //透明度:较深;下
//        this.drawGradientRect(width + length, height + 1, leftEliminate, height, var9, var9);
//        //透明度:最浅;中
//        this.drawGradientRect(width + length, height, leftEliminate, topEliminate, var9, var9);
//
//        //边框颜色
//        int var10 = 1347420415;
//        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
//        //绘制边框
//        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
//        //五行代码绘制四条线
//        //左
//        this.drawGradientRect(leftEliminate + 1, height, leftEliminate, topEliminate, var10, var11);
//        //右
//        this.drawGradientRect(width + length, height, width - 1 + length, topEliminate, var10, var11);
//        //上
//        this.drawGradientRect(width + length, topEliminate + 1, leftEliminate + 1, topEliminate, var10, var10);
//        //下
//        this.drawGradientRect(width + length, height, leftEliminate, height - 1, var11, var11);
//
//    }

}
