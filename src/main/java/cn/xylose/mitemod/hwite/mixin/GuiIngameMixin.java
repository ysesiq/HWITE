package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.HwiteMod;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

import static cn.xylose.mitemod.hwite.HwiteMod.*;
import static net.minecraft.GuiIngame.server_load;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {

    @Final
    @Shadow
    private Minecraft mc;
    @Shadow
    private static final RenderItem itemRenderer = new RenderItem();


    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderHWITEHud(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        FontRenderer fontRenderer = this.mc.fontRenderer;
//        if (server_load >= 0 || this.mc.gameSettings.gui_mode == 0) {
//            ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
//            String text = server_load + "%";
//            this.drawString(fontRenderer, text, sr.getScaledWidth() - fontRenderer.getStringWidth(text) - 2, 2, 0xE0E0E0);
//        }

        int start = 12;
        if (this.mc.gameSettings.gui_mode == 0) {

            //draw text
            this.drawString(fontRenderer, info, 183, start, 0xE0E0E0);
            this.drawString(fontRenderer, info_line_1, 183, start + 10, 0xE0E0E0);
            this.drawString(fontRenderer, info_line_2, 183, start + 20, 0xE0E0E0);

            //draw model
            if (entityInfo != null) {
                //x, y, size, ?, ?
                GuiInventory.func_110423_a(160, 43, 18, 0, 0, HwiteMod.entityInfo);
            }
            if (!Objects.equals(info, "") && entityInfo != null) {
                this.renderBoxEntity(par3, par4);
            } else if (!Objects.equals(info, "")) {
                this.renderBox(par3, par4);
//                itemRenderer.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), , par2, par3);

            }
        }
    }

    @Unique
    private void renderBoxEntity(int mouseX, int mouseY) {
        //绘制一个以屏幕左上角为零点的方形,通过剔除实现更丰富的形状

        //边缘(底层)颜色
        int var9 = -267386864;
        var9 = var9 & 16777215 | -369098752;
        //绘制边缘(底层)
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码代表中间跟四个方向
        //透明度:最深;左
        this.drawGradientRect(140, 45, 139, 8, var9, var9);
        //透明度:较浅;右
        this.drawGradientRect(271, 45, 270, 8, var9, var9);
        //透明度:深;上
        this.drawGradientRect(270, 8, 140, 7, var9, var9);
        //透明度:较深;下
        this.drawGradientRect(270, 46, 140, 45, var9, var9);
        //透明度:最浅;中
        this.drawGradientRect(270, 45, 140, 8, var9, var9);

        //边框颜色
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        //绘制边框
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码绘制四条线
        //左
        this.drawGradientRect(141, 45, 140, 8, var10, var11);
        //右
        this.drawGradientRect(270, 45, 269, 8, var10, var11);
        //上
        this.drawGradientRect(270, 9, 140, 8, var10, var10);
        //下
        this.drawGradientRect(270, 45, 140, 44, var11, var11);

    }

    @Unique
    private void renderBox(int mouseX, int mouseY) {
        //绘制一个以屏幕左上角为零点的方形,通过剔除实现更丰富的形状

        //边缘(底层)颜色
        int var9 = -267386864;
        var9 = var9 & 16777215 | -369098752;
        //绘制边缘(底层)
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码代表中间跟四个方向
        //透明度:最深;左
        this.drawGradientRect(178, 45, 177, 8, var9, var9);
        //透明度:较浅;右
        this.drawGradientRect(269, 45, 268, 8, var9, var9);
        //透明度:深;上
        this.drawGradientRect(268, 8, 178, 7, var9, var9);
        //透明度:较深;下
        this.drawGradientRect(268, 46, 178, 45, var9, var9);
        //透明度:最浅;中
        this.drawGradientRect(268, 45, 178, 8, var9, var9);

        //边框颜色
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        //绘制边框
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码绘制四条线
        //左
        this.drawGradientRect(179, 45, 178, 8, var10, var11);
        //右
        this.drawGradientRect(268, 45, 267, 8, var10, var11);
        //上
        this.drawGradientRect(268, 9, 179, 8, var10, var10);
        //下
        this.drawGradientRect(268, 45, 178, 44, var11, var11);

    }

    //已弃用
//    @Unique
//    private void renderBox(int x, int y) {
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        Tessellator tessellator = Tessellator.instance;
//        this.renderQuad(tessellator, 160, 40, 20, 10, 60);
////        this.renderQuad(tessellator, par4 + 2, par5 + 13, 12, 1, var11);
////        this.renderQuad(tessellator, par4 + 2, par5 + 13, var12, 1, var10);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//
//    @Unique
//    private void renderQuad(Tessellator tessellator, int x, int y, int width, int height, int color) {
//        tessellator.startDrawingQuads();
//        tessellator.setColorOpaque_I(color);
//        tessellator.addVertex(x, y, 0.0D);
//        tessellator.addVertex(x, y + height, 0.0D);
//        tessellator.addVertex(x + width, y + height, 0.0D);
//        tessellator.addVertex(x + width, y, 0.0D);
//        tessellator.draw();
//    }
}
