package cn.xylose.mitemod.hwite.mixin;

import com.github.Debris.ModernMite.ModernMiteConfig;
import cn.xylose.mitemod.hwite.client.HwiteModClient;
import cn.xylose.mitemod.hwite.RenderItemHwite;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

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

    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderHWITEHud(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        FontRenderer fontRenderer = this.mc.fontRenderer;

        int info_x = InfoX.get();
        int info_y = InfoY.get();
        int entity_info_x = EntityInfoX.get();
        int entity_info_y = EntityInfoY.get();
        int entity_info_size = EntityInfoSize.get();
        int block_info_x = BlockInfoX.get();
        int block_info_y_big = BlockInfoYBig.get();
        int block_info_y_small = BlockInfoYSmall.get();

        if (this.mc.gameSettings.gui_mode == 0 && !this.mc.gameSettings.keyBindPlayerList.pressed) {
            ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
            int var6 = scaledResolution.getScaledWidth();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(canBreakTexPath);
            this.drawTexturedModalRect(var6 - 17, 2, 0, 0, 16, 16);

            //draw text
            this.drawString(fontRenderer, info, info_x, info_y, 0xE0E0E0);
            this.drawString(fontRenderer, info_line_1, info_x, info_y + 10, 0xE0E0E0);
            this.drawString(fontRenderer, info_line_2, info_x, info_y + 20, 0xE0E0E0);
//            this.drawString(fontRenderer, break_info, info_x + 80, info_y, 0xE0E0E0);

            //draw model
            if (!Objects.equals(info, "") && entityInfo != null) {
                //x, y, size, ?, ?
                GuiInventory.func_110423_a(entity_info_x, entity_info_y, entity_info_size, 0, 0, HwiteModClient.entityInfo);
            }
            if (!Objects.equals(info, "") && entityInfo != null) {
                this.renderBox(par3, par4);
            } else if (Objects.equals(info_line_2, "") && !Objects.equals(info, "")) {
                this.renderBox(par3, par4);
                itemRenderBlocks.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), blockInfo, block_info_x, block_info_y_small);
            } else if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
                this.renderBox(par3, par4);
            } else if (!Objects.equals(info, "")) {
                this.renderBox(par3, par4);
                itemRenderBlocks.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), blockInfo, block_info_x, block_info_y_big);
            }
        }
    }

    @Unique
    private void renderBox(int mouseX, int mouseY) {
        //绘制一个以屏幕左上角为零点的方形,通过剔除实现更丰富的形状

        ScaledResolution scaledResolution = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int scaledWidth = scaledResolution.getScaledWidth();
        int scaledHeight = scaledResolution.getScaledHeight();

        int length;
        int width = BGWidth.get();
//        int width = scaledWidth / 2;
        int height = BGHeight.get();
        int leftEliminate = BGLeftEliminate.get();
        int topEliminate = BGTopEliminate.get();

        length = Math.max(info.length(), Math.max(info_line_1.length(), info_line_2.length()));
//        if (ModernMiteConfig.ASCIIFont.getBooleanValue()) {
//            length *= 1.5;
//        }

        if (Objects.equals(info_line_2, "") && !Objects.equals(info, "")) {
            height -= 10;
        } else if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
            height -= 20;
        }

        if (!Objects.equals(info, "") && entityInfo != null) {
            leftEliminate -= 20;
            width += 2;
            height = 45;
        }

        if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
            leftEliminate += 18;
        }

        //边缘(底层)颜色
        int var9 = -267386864;
        var9 = var9 & 16777215 | -369098752;
        //绘制边缘(底层)
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码代表中间跟四个方向
        //透明度:最深;左
        this.drawGradientRect(leftEliminate, height, leftEliminate - 1, topEliminate, var9, var9);
        //透明度:较浅;右
        this.drawGradientRect(width + 1 + length, height, width + length, topEliminate, var9, var9);
        //透明度:深;上
        this.drawGradientRect(width + length, topEliminate, leftEliminate, topEliminate - 1, var9, var9);
        //透明度:较深;下
        this.drawGradientRect(width + length, height + 1, leftEliminate, height, var9, var9);
        //透明度:最浅;中
        this.drawGradientRect(width + length, height, leftEliminate, topEliminate, var9, var9);

        //边框颜色
        int var10 = 1347420415;
        int var11 = (var10 & 16711422) >> 1 | var10 & -16777216;
        //绘制边框
        ///第一个参数=宽度,第二个参数=长度,第三个参数=左边剔除宽度(剔除宽度为第二个参数),第四个参数=顶部剔除长度(剔除长度为第二个参数),第五,六个参数=颜色(不是特别明白)
        //五行代码绘制四条线
        //左
        this.drawGradientRect(leftEliminate + 1, height, leftEliminate, topEliminate, var10, var11);
        //右
        this.drawGradientRect(width + length, height, width - 1 + length, topEliminate, var10, var11);
        //上
        this.drawGradientRect(width + length, topEliminate + 1, leftEliminate + 1, topEliminate, var10, var10);
        //下
        this.drawGradientRect(width + length, height, leftEliminate, height - 1, var11, var11);

    }

//    private void renderCanOrCannotBreak(int x, int y) {
//        GL11.glDisable(2929);
//        GL11.glDepthMask(false);
//        GL11.glBlendFunc(770, 771);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GL11.glDisable(3008);
//        this.mc.getTextureManager().bindTexture(canBreakTexPath);
//        Tessellator tessellator = Tessellator.instance;
//        tessellator.startDrawingQuads();
//        tessellator.addVertex(x, y, 0.0D);
//        tessellator.addVertex(x, y, 0.0D);
//        tessellator.addVertex(x, y, 0.0D);
//        tessellator.addVertex(x, y, 0.0D);
//        tessellator.draw();
//        GL11.glDepthMask(true);
//        GL11.glEnable(2929);
//        GL11.glEnable(3008);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//    }

    //已弃用
//    @Unique
//    private void renderCanOrCannotBreak(int x, int y) {
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        Tessellator tessellator = Tessellator.instance;
//        this.renderQuad(tessellator, 160, 40, 20, 10, 60);
////        this.renderQuad(tessellator, par4 + 2, par5 + 13, 12, 1, var11);
////        this.renderQuad(tessellator, par4 + 2, par5 + 13, var12, 1, var10);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//    }
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
