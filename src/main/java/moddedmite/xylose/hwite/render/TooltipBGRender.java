package moddedmite.xylose.hwite.render;

import moddedmite.xylose.hwite.config.EnumHUDTheme;
import moddedmite.xylose.hwite.info.HwiteInfo;
import moddedmite.xylose.hwite.api.IBreakingProgress;
import moddedmite.xylose.hwite.util.DisplayUtil;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static moddedmite.xylose.hwite.api.SpecialChars.patternTab;
import static moddedmite.xylose.hwite.config.HwiteConfigs.*;
import static moddedmite.xylose.hwite.render.Tooltip.TabSpacing;

public class TooltipBGRender extends Gui {
    private static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    public static int x;
    public static int y;
    public static int w;
    public static int h;
    public static int var15;
    public static int var24;
    public static int var25;
    public static final RenderItem itemRenderer = new RenderItem();

    public TooltipBGRender() {
    }

    public void drawIcons(RaycastCollision rc, int x, int y) {
        if (rc != null && rc.getEntityHit() instanceof EntityLivingBase) {
            Minecraft mc = Minecraft.getMinecraft();
            GuiIngame guiIngame = mc.ingameGUI;
            EntityLivingBase entity = (EntityLivingBase) rc.getEntityHit();
            Random random = new Random();
            boolean var3 = entity.hurtResistantTime / 3 % 2 == 1;

            if (entity.hurtResistantTime < 10) {
                var3 = false;
            }

            int health = MathHelper.ceiling_float_int(entity.getHealth());
            int prevHealth = MathHelper.ceiling_float_int(entity.prevHealth);
            AttributeInstance maxHealth = entity.getEntityAttribute(SharedMonsterAttributes.maxHealth);
            float maxHealthAttribute = (float) maxHealth.getAttributeValue();
            float absorptionAmount = entity.getAbsorptionAmount();
            int heartRow = MathHelper.ceiling_float_int((maxHealthAttribute + absorptionAmount) / 2.0F / 10.0F);
            var15 = Math.max(10 - (heartRow - 2), 3);
            float var17 = absorptionAmount;
            int var20 = -1;
            if (entity.isPotionActive(Potion.regeneration)) {
                var20 = guiIngame.updateCounter % MathHelper.ceiling_float_int(maxHealthAttribute + 5.0F);
            }
            int textureX;
            int heart;
            int var23;

            mc.getTextureManager().bindTexture(icons);
            for (heart = MathHelper.ceiling_float_int((maxHealthAttribute + absorptionAmount) / 2.0F) - 1; heart >= 0; --heart) {
                textureX = 16;

                if (entity.isPotionActive(Potion.poison)) {
                    textureX += 36;
                } else if (entity.isPotionActive(Potion.wither)) {
                    textureX += 72;
                }

                byte var26 = 0;

                if (var3) {
                    var26 = 1;
                }

                var23 = MathHelper.ceiling_float_int((float) (heart + 1) / 10.0F) - 1;
                var25 = x + heart % 10 * 8;
                var24 = y + var23 * var15;

                if (heart == var20) {
                    var24 -= 2;
                }

                byte var27 = 0;

                if (entity.getMaxHealth() <= 20) {
                    HwiteInfo.renderHealth = true;
                    if ((float) heart < entity.getMaxHealth() / 2.0F) {
                        this.drawTexturedModalRect(var25, var24, 16 + var26 * 9, 9 * var27, 9, 9);
                    }

                    if (var3) {
                        if (heart * 2 + 1 < prevHealth) {
                            this.drawTexturedModalRect(var25, var24, textureX + 54, 9 * var27, 9, 9);
                        }

                        if (heart * 2 + 1 == prevHealth) {
                            this.drawTexturedModalRect(var25, var24, textureX + 63, 9 * var27, 9, 9);
                        }
                    }

                    if (var17 > 0.0F) {
                        if (var17 == absorptionAmount && absorptionAmount % 2.0F == 1.0F) {
                            this.drawTexturedModalRect(var25, var24, textureX + 153, 9 * var27, 9, 9);
                        } else {
                            this.drawTexturedModalRect(var25, var24, textureX + 144, 9 * var27, 9, 9);
                        }

                        var17 -= 2.0F;
                    } else {
                        if (heart * 2 + 1 < health) {
                            this.drawTexturedModalRect(var25, var24, textureX + 36, 9 * var27, 9, 9);
                        }

                        if (heart * 2 + 1 == health) {
                            this.drawTexturedModalRect(var25, var24, textureX + 45, 9 * var27, 9, 9);
                        }
                    }
                }
            }
        }
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

        Point pos = new Point(TooltipX.getIntegerValue(), TooltipY.getIntegerValue());

        int paddingBlockW = HwiteInfo.hasIcon ? 29 : 13;
        int health = Math.min(HwiteInfo.updateEntityLivingBaseMaxHealth(Minecraft.theMinecraft.objectMouseOver), 20);
        int paddingHealthW = HwiteInfo.renderHealth ? health * 5 : 0;
        int paddingW = Math.max(paddingBlockW, paddingHealthW);
        int paddingH = HwiteInfo.hasIcon ? 24 : 0;
        int offsetX = HwiteInfo.hasIcon ? 24 : 6;

        w = paddingW > maxStringW ? paddingW : maxStringW + paddingW;
        h = par1List.size() * 11 + 3;

        Dimension size = DisplayUtil.displaySize();
        x = ((int) (size.width / (float) TooltipScale.getDoubleValue()) - w - 1) * pos.x / 100;
        y = ((int) (size.height / (float) TooltipScale.getDoubleValue()) - h - 1) * pos.y / 100;

        if (BossStatus.bossName != null && BossStatus.statusBarLength > 0 && Minecraft.inDevMode() && mc.gameSettings.gui_mode == 0) {
            y += 20;
        } else if (BossStatus.bossName != null && BossStatus.statusBarLength > 0) {
            y += 20;
        } else if (Minecraft.inDevMode() && mc.gameSettings.gui_mode == 0) {
            y += 10;
        }

        if (y + var8 + 6 > height) {
            y = height - var8 - 6;
        }

        itemRenderer.zLevel = 300.0F;
        Color4f tooltipBGColor;
        Color4f tooltipFrameColorTop;
        Color4f tooltipFrameColorBottom;
        float alpha = (float) TooltipAlpha.getIntegerValue() / 100;
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
            fontRenderer.drawStringWithShadow(var13, HwiteInfo.hasIcon ? x + 25 : x + 8, y + 4, -1);

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
