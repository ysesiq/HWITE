package cn.xylose.mitemod.hwite.render;

import cn.xylose.mitemod.hwite.config.HwiteConfigs;
import cn.xylose.mitemod.hwite.info.HwiteInfo;
import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import cn.xylose.mitemod.hwite.render.util.EnumRenderFlag;
import cn.xylose.mitemod.hwite.render.util.ScreenConstants;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.render.HUDBackGroundRender.drawTooltipBackGround;
import static cn.xylose.mitemod.hwite.render.util.EnumRenderFlag.Big;
import static cn.xylose.mitemod.hwite.render.util.EnumRenderFlag.Small;

public class HUDRenderer {

    public static void RenderHWITEHud(Gui gui, Minecraft mc, double zLevel) {
        ArrayList<String> list = new ArrayList<>();
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        int block_info_x = ScreenConstants.getBlockInfoX(screenWidth);

        //draw model
        boolean mainInfoNotEmpty = !Objects.equals(HwiteInfo.infoMain, "");
        if (mainInfoNotEmpty && HwiteInfo.entityInfo != null && EntityRender.getBooleanValue()) {
            //x, y, size, ?, ?
            GuiInventory.func_110423_a(EntityInfoX.getIntegerValue(), EntityInfoY.getIntegerValue(), EntityInfoSize.getIntegerValue(), 0, 0, HwiteInfo.entityInfo);
        }

        //draw text and tooltip background
        EnumRenderFlag enumRenderFlag = addInfoToList(list);
        drawTooltipBackGround(list, ScreenConstants.getHudX(screenWidth), ScreenConstants.getHudY(), false, mc, zLevel);
        RenderItem renderItem = new RenderItem();
        if (HwiteInfo.blockInfo != null) {
            switch (enumRenderFlag) {
                case Small, Big -> {
                    ItemStack itemStack = HwiteInfo.blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(HwiteInfo.blockPosX, HwiteInfo.blockPosY, HwiteInfo.blockPosZ));
                        renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, block_info_x, (int) (HUDBackGroundRender.stringWidth / 3.5));
                        renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, block_info_x, (int) (HUDBackGroundRender.stringWidth / 3.5));
                }
            }
            GL11.glDisable(GL11.GL_LIGHTING);
        }
    }

    private static EnumRenderFlag addInfoToList(List<String> list) {
        boolean mainInfoEmpty = Objects.equals(HwiteInfo.infoMain, "");
        if (mainInfoEmpty) {
            return EnumRenderFlag.Nothing;
        }

        int breakProgress = (int) (((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress() * 100);
        boolean line2Empty = Objects.equals(HwiteInfo.info_line_2, "");
        boolean line1Empty = Objects.equals(HwiteInfo.info_line_1, "");
        boolean line1IsABlank = Objects.equals(HwiteInfo.info_line_1, " ");
        boolean line2IsABlank = Objects.equals(HwiteInfo.info_line_2, " ");
        boolean breadInfoEmpty = Objects.equals(HwiteInfo.break_info, "");
        boolean blockInfoNonNull = HwiteInfo.blockInfo != null;

        boolean smallFlag = false;
        boolean bigFlag = false;

        if (HwiteInfo.entityInfo != null) {
            list.add(HwiteInfo.infoMain);
            tryAddExtraInfo(list, breakProgress);
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
            }
        } else if (line2Empty) {
            if (!breadInfoEmpty && CanBreak.getBooleanValue()) {
                list.add(HwiteInfo.infoMain + "  " + HwiteInfo.break_info);
                tryAddExtraInfo(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
            } else {
                list.add(HwiteInfo.infoMain);
                tryAddExtraInfo(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
            }
            if (BlockRender.getBooleanValue() && blockInfoNonNull) {
                smallFlag = true;
            }
        } else if (line1IsABlank && line2IsABlank) {
            list.add(HwiteInfo.infoMain);
            if (breakProgress > 0) {
                list.add(String.format("%d", breakProgress) + "%");
            }
        } else {
            if (!breadInfoEmpty && CanBreak.getBooleanValue()) {
                list.add(HwiteInfo.infoMain + "  " + HwiteInfo.break_info);
                tryAddExtraInfo(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
//                } else if (isViewMode && ViewMode.getBooleanValue()) {
//                    list.add(info);
//                    list.add(modInfo);
            } else {
                list.add(HwiteInfo.infoMain);
                tryAddExtraInfo(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
                list.add(HwiteInfo.info_line_2);
            }
            if (BlockRender.getBooleanValue() && blockInfoNonNull) {
                bigFlag = true;
            }
        }

        list.add(HwiteInfo.modInfo);

        if (smallFlag) {
            return Small;
        }
        if (bigFlag) {
            return Big;
        }
        return EnumRenderFlag.Nothing;
    }

    private static void tryAddExtraInfo(List<String> list, int breakProgress) {
        tryAddBreakProgress(list, breakProgress);
        tryAddGrowthValue(list);
        tryAddRedstoneValue(list);
        tryAddSpawnerValue(list);
    }

    private static void tryAddBreakProgress(List<String> list, int breakProgress) {
        if (breakProgress > 0 && BreakProgress.getBooleanValue()) {
            list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
        }
    }

    private static void tryAddGrowthValue(List<String> list) {
        if (GrowthValue.getBooleanValue() && !Objects.equals(HwiteInfo.growth_info, "")) {
            list.add(HwiteInfo.growth_info);
        }
    }

    private static void tryAddRedstoneValue(List<String> list) {
        if (Redstone.getBooleanValue() && !Objects.equals(HwiteInfo.redstone_info, "")) {
            list.add(HwiteInfo.redstone_info);
        }
    }

    private static void tryAddSpawnerValue(List<String> list) {
        if (SpawnerType.getBooleanValue() && !Objects.equals(HwiteInfo.spawner_info, "")) {
            list.add(HwiteInfo.spawner_info);
        }
    }
}
