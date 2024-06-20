package cn.xylose.mitemod.hwite.render;

import cn.xylose.mitemod.hwite.info.HwiteInfo;
import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;
import java.util.Objects;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;

public class HUDBackGroundRender extends Gui {

    private static final RenderItem itemRenderer = new RenderItem();

//    public void drawHWITEHoveringText(String par1Str, int par2, int par3, Minecraft mc) {
//        drawTooltipBackGround(Arrays.asList(new String[]{par1Str}), par2, par3, mc);
//    }
//
//    public static void drawTooltipBackGround(List par1List, int par2, int par3, Minecraft mc) {
//        drawTooltipBackGround(par1List, par2, par3, true, mc);
//    }

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

        int stringWidth;
        for (String s : par1List) {
            stringWidth = fontRenderer.getStringWidth(s);

            if (stringWidth > var4) {
                var4 = stringWidth;
            }
        }

        int stringWidth1 = par2 + 12;
        stringWidth = par3 - 12;
        int var8 = 8;

        if (par1List.size() > 1) {
            var8 += 2 + (par1List.size() - 1) * 10;
        }

        if (!has_title) {
            var8 -= 2;
        }

        if (stringWidth1 + var4 > width) {
            stringWidth1 -= 28 + var4;
        }

        if (stringWidth + var8 + 6 > height) {
            stringWidth = height - var8 - 6;
        }

        int BlockRenderLeftEliminate = 0;
        if (HwiteInfo.entityInfo == null) {
            if (shiftMoreInfo.getBooleanValue()) {
                if (!Objects.equals(HwiteInfo.infoMain, "") && !Objects.equals(HwiteInfo.break_info, "  ") && BlockRender.get()) {
                    BlockRenderLeftEliminate += 20;
                } else if (!Objects.equals(HwiteInfo.break_info, " ") && BlockRender.get()) {
                    BlockRenderLeftEliminate += 20;
                }
            } else {
                if (Objects.equals(HwiteInfo.info_line_2, "") && !Objects.equals(HwiteInfo.infoMain, "") && !Objects.equals(HwiteInfo.info_line_1, "") && !Objects.equals(HwiteInfo.break_info, "  ") && BlockRender.get()) {
                    BlockRenderLeftEliminate += 20;
                } else if (!Objects.equals(HwiteInfo.info_line_2, "") && !Objects.equals(HwiteInfo.break_info, " ") && BlockRender.get()) {
                    BlockRenderLeftEliminate += 20;
                }
            }
        }
//            zLevel = 300.0F;
        itemRenderer.zLevel = 300.0F;
        int var9 = HUDBGColor.get();
        var9 = var9 & HUDBGColor1.get() | HUDBGColor2.get();
        if (HUDBackGround.get()) {
            //中
            if (HUDCentralBackground.get()) {
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 + var4 + 3, stringWidth + var8 + 3, var9, var9, zLevel);
            }
            if (HUDRoundedRectangle.get()) {
                //上
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 4, stringWidth1 + var4 + 3, stringWidth - 3, var9, var9, zLevel);
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, stringWidth1 + var4 + 3, stringWidth + var8 + 4, var9, var9, zLevel);
                drawGradientRect(stringWidth1 - 4 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, var9, var9, zLevel);
                //右
                drawGradientRect(stringWidth1 + var4 + 3, stringWidth - 3, stringWidth1 + var4 + 4, stringWidth + var8 + 3, var9, var9, zLevel);
            }
            int var10 = HUDFrameColor.get();
            int var11 = (var10 & HUDFrameColor1.get()) >> 1 | var10 & HUDFrameColor2.get();
            if (HUDFrame.get()) {
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3 + 1, stringWidth1 - 3 + 1 - BlockRenderLeftEliminate, stringWidth + var8 + 3 - 1, var10, var11, zLevel);
                drawGradientRect(stringWidth1 + var4 + 2, stringWidth - 3 + 1, stringWidth1 + var4 + 3, stringWidth + var8 + 3 - 1, var10, var11, zLevel);
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 + var4 + 3, stringWidth - 3 + 1, var10, var10, zLevel);
                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 2, stringWidth1 + var4 + 3, stringWidth + var8 + 3, var11, var11, zLevel);
            }
        }
        float breakProgress = ((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress();
        if (breakProgress > 0 && BreakProgressLine.get()) {
            int progress = (int) (breakProgress * 100F);
//                drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, stringWidth1 + var4 + 3, stringWidth + var8 + 4, var9, var9, zLevel);
            drawGradientRect(
                    progress + stringWidth1 - 3 - BlockRenderLeftEliminate,
                    stringWidth + var8 + 3,
                    stringWidth1 + var4 + 3,
                    stringWidth + var8 + 4,
                    0xFFFFFFFF, 0xFFFFFFFF, zLevel);
//
//                drawHorizontalLineHwite(var15 + 10, var15 + 110, lineHeight, 0xFFFFFFFF);
//                drawHorizontalLineHwite(var15 + 10, progress + var15 + 10, lineHeight, 0xFF000000);
//                drawHorizontalLineHwite(stringWidth1 + var4 + 3, progress + stringWidth1 - BlockRenderLeftEliminate, stringWidth + var8 + 3, 0xFFFFFFFF);
        }
        for (int var12 = 0; var12 < par1List.size(); ++var12) {
            String var13 = par1List.get(var12);
            fontRenderer.drawStringWithShadow(var13, stringWidth1, stringWidth, -1);

            if (var12 == 0 && has_title) {
                stringWidth += 2;
            }

            stringWidth += 10;
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

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6, double zLevel) {
        float var7 = (float) (par5 >> 24 & 255) / 255.0F;
        float var8 = (float) (par5 >> 16 & 255) / 255.0F;
        float var9 = (float) (par5 >> 8 & 255) / 255.0F;
        float var10 = (float) (par5 & 255) / 255.0F;
        float var11 = (float) (par6 >> 24 & 255) / 255.0F;
        float var12 = (float) (par6 >> 16 & 255) / 255.0F;
        float var13 = (float) (par6 >> 8 & 255) / 255.0F;
        float var14 = (float) (par6 & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex(par3, par2, zLevel);
        var15.addVertex(par1, par2, zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex(par1, par4, zLevel);
        var15.addVertex(par3, par4, zLevel);
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
