package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.RenderItemHwite;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

import static cn.xylose.mitemod.hwite.client.HwiteModClient.*;
import static cn.xylose.mitemod.hwite.HwiteConfigs.*;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {

    @Final
    @Shadow
    private Minecraft mc;

    private RenderItemHwite itemRenderBlocks = new RenderItemHwite();
    private static final ResourceLocation canBreakTexPath = new ResourceLocation("textures/gui/cannot_break.png");
    private static final ResourceLocation cannotBreakTexPath = new ResourceLocation("textures/gui/can_break.png");
    private static RenderItem itemRenderer = new RenderItem();

    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderHWITEHud(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        FontRenderer fontRenderer = this.mc.fontRenderer;
        ArrayList list = new ArrayList();
        ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int width = scaledResolution.getScaledWidth();

        int hud_x;
        int hud_y;
        if (HUDPos.get()) {
            hud_x = HUDX.get();
            hud_y = HUDY.get();
        } else {
            hud_x = scaledResolution.getScaledWidth() / 2 - 50;
            hud_y = scaledResolution.getScaledHeight() - (scaledResolution.getScaledHeight() - 20);
        }
        int entity_info_x = EntityInfoX.get();
        int entity_info_y = EntityInfoY.get();
        int entity_info_size = EntityInfoSize.get();
        int block_info_x = BlockInfoX.get();
        int block_info_y_big = BlockInfoYBig.get();
        int block_info_y_small = BlockInfoYSmall.get();

        if (this.mc.gameSettings.gui_mode == 0 && !this.mc.gameSettings.keyBindPlayerList.pressed && InfoHide.get()) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(canBreakTexPath);
            this.drawTexturedModalRect(width - 17, 2, 0, 0, 16, 16);

            //draw model & text
            if (!Objects.equals(info, "") && entityInfo != null && !EntityInfoHide.get()) {
                //x, y, size, ?, ?
                GuiInventory.func_110423_a(entity_info_x, entity_info_y, entity_info_size, 0, 0, entityInfo);
            }
            if (!Objects.equals(info, "") && entityInfo != null) {
                list.add(info);
                if (!Objects.equals(info_line_1, "")) {
                    list.add(info_line_1);
                }
                list.add(modInfo);
                this.drawTooltipBackGround(list, hud_x, hud_y, false);
            } else if (Objects.equals(info_line_2, "") && !Objects.equals(info, "")) {
                list.add(info + "       " + break_info);
                if (!Objects.equals(info_line_1, "")) {
                    list.add(info_line_1);
                }
                list.add(modInfo);
                this.drawTooltipBackGround(list, hud_x, hud_y, false);
                if (!BlockInfoHide.get()) {
                    itemRenderBlocks.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), blockInfo, block_info_x + 20, block_info_y_small);
                }
            } else if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
                list.add(info);
                list.add(modInfo);
                this.drawTooltipBackGround(list, hud_x, hud_y, false);
            } else if (!Objects.equals(info, "")) {
                list.add(info + "       " + break_info);
                if (!Objects.equals(info_line_1, "") && !Objects.equals(info_line_2, "")) {
                    list.add(info_line_1);
                    list.add(info_line_2);
                }
                list.add(modInfo);
                this.drawTooltipBackGround(list, hud_x, hud_y, false);
                if (!BlockInfoHide.get()) {
                    itemRenderBlocks.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), blockInfo, block_info_x + 20, block_info_y_big);
                }
            }
        }
    }

    @Unique
    private void drawHWITEHoveringText(String par1Str, int par2, int par3) {
        drawTooltipBackGround(Arrays.asList(new String[]{par1Str}), par2, par3);
    }

    @Unique
    private void drawTooltipBackGround(List par1List, int par2, int par3) {
        drawTooltipBackGround(par1List, par2, par3, true);
    }

    @Unique
    private void drawTooltipBackGround(List par1List, int par2, int par3, boolean has_title) {
        FontRenderer fontRenderer = this.mc.fontRenderer;
        ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        if (!par1List.isEmpty()) {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int var4 = 0;
            Iterator var5 = par1List.iterator();
            int stringWidth;

            while (var5.hasNext()) {
                String var14 = (String)var5.next();
                stringWidth = fontRenderer.getStringWidth(var14);

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
            if (entityInfo == null) {
                if (Objects.equals(info_line_2, "") && !Objects.equals(info, "") && !Objects.equals(break_info, "  ") && !BlockInfoHide.get()) {
                    BlockRenderLeftEliminate += 17;
                } else if (!Objects.equals(info_line_2, "") && !BlockInfoHide.get()) {
                    BlockRenderLeftEliminate += 17;
                }
            }

            zLevel = 300.0F;
            itemRenderer.zLevel = 300.0F;
            int var9 = -267386864;
            var9 = var9 & 16777215 | -369098752;
            //上
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 4, stringWidth1 + var4 + 3, stringWidth - 3, var9, var9);
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, stringWidth1 + var4 + 3, stringWidth + var8 + 4, var9, var9);
            //中
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 + var4 + 3, stringWidth + var8 + 3, var9, var9);
            drawGradientRect(stringWidth1 - 4 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 3, var9, var9);
            //右
            drawGradientRect(stringWidth1 + var4 + 3, stringWidth - 3, stringWidth1 + var4 + 4, stringWidth + var8 + 3, var9, var9);
            int var10 = 1347420415;
            int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3 + 1, stringWidth1 - 3 + 1 - BlockRenderLeftEliminate , stringWidth + var8 + 3 - 1, var10, var11);
            drawGradientRect(stringWidth1 + var4 + 2, stringWidth - 3 + 1, stringWidth1 + var4 + 3, stringWidth + var8 + 3 - 1, var10, var11);
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth - 3, stringWidth1 + var4 + 3, stringWidth - 3 + 1, var10, var10);
            drawGradientRect(stringWidth1 - 3 - BlockRenderLeftEliminate, stringWidth + var8 + 2, stringWidth1 + var4 + 3, stringWidth + var8 + 3, var11, var11);

            for (int var12 = 0; var12 < par1List.size(); ++var12) {
                String var13 = (String)par1List.get(var12);
                fontRenderer.drawStringWithShadow(var13, stringWidth1, stringWidth, -1);

                if (var12 == 0 && has_title) {
                    stringWidth += 2;
                }

                stringWidth += 10;
            }

            zLevel = 0.0F;
            itemRenderer.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    //已弃用
//    @Unique
//    private void renderBox(int mouseX, int mouseY) {
//        //绘制一个以屏幕左上角为零点的方形,通过剔除实现更丰富的形状
//
//        ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
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
//        if (BlockInfoHide.get() && Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "") && Objects.equals(entityInfo, null)) {
//            leftEliminate += 20;
//        } else if ((BlockInfoHide.get() && Objects.equals(info_line_2, "") && !Objects.equals(info, "") && Objects.equals(entityInfo, null))) {
//            leftEliminate += 20;
//        } else if ((BlockInfoHide.get() && !Objects.equals(info_line_2, "") && !Objects.equals(info, "") && Objects.equals(entityInfo, null))) {
//            leftEliminate += 20;
//        } else if (EntityInfoHide.get() && !Objects.equals(info, "") && entityInfo != null) {
//            leftEliminate += 40;
//            height -= 10;
//        }
//
//        if (!BlockInfoHide.get()) {
//            leftEliminate += 20;
//            width += 20;
//        } else if (!EntityInfoHide.get() && entityInfo != null) {
//            leftEliminate += 35;
//            width += 35;
//        }
//
//        if (!GuiScreen.isShiftKeyDown() && BlockInfoHide.get() && !(break_info == " ")) {
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
