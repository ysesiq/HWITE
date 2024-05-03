package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.HwiteMod;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static cn.xylose.mitemod.hwite.HwiteMod.info;
import static cn.xylose.mitemod.hwite.HwiteMod.info_line_1;
import static net.minecraft.GuiIngame.server_load;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {

    @Final
    @Shadow
    private Minecraft mc;

    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderHWITEHud(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
//        this.renderBox(par3, par4);
        FontRenderer fontRenderer = this.mc.fontRenderer;

        if (server_load >= 0) {
            ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            String text = server_load + "%";
            this.drawString(fontRenderer, text, sr.getScaledWidth() - fontRenderer.getStringWidth(text) - 2, 2, 0xE0E0E0);
        }

        //draw text
        this.drawString(fontRenderer, info, 175, 12, 0xE0E0E0);
        this.drawString(fontRenderer, info_line_1, 175, 20, 0xE0E0E0);

        //draw model
        if (HwiteMod.entityInfo != null)
            GuiInventory.func_110423_a(165, 40, 18, 0, 0, HwiteMod.entityInfo);
    }

//    @Unique
//    private void renderBox(int x, int y) {
////        GL11.glDisable(GL11.GL_DEPTH_TEST);
////        GL11.glDisable(GL11.GL_TEXTURE_2D);
////        Tessellator tessellator = Tessellator.instance;
////        this.renderQuad(tessellator, 160, 40, 20, 10, 60);
//////        this.renderQuad(tessellator, par4 + 2, par5 + 13, 12, 1, var11);
//////        this.renderQuad(tessellator, par4 + 2, par5 + 13, var12, 1, var10);
////        GL11.glEnable(GL11.GL_TEXTURE_2D);
////        GL11.glEnable(GL11.GL_DEPTH_TEST);
////        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GL11.glDisable(32826);
//        GL11.glDisable(2896);
//        GL11.glDisable(2929);
//        int var4 = 0;
//        int var15 = 1;
//        int var14 = x + 12;
//        var15 = y - 12;
//        int var8 = 8;
//        int var9 = -267386864;
//        var9 = var9 & 16777215 | -369098752;
//        this.drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
//        this.drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
//        this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
//        this.drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
//        this.drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
//        int var10 = 1347420415;
//        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
//        this.drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
//        this.drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
//        this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
//        this.drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);
//        this.zLevel = 0.0F;
//        GL11.glEnable(2896);
//        GL11.glEnable(2929);
//        RenderHelper.enableStandardItemLighting();
//        GL11.glEnable(32826);
//    }



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
