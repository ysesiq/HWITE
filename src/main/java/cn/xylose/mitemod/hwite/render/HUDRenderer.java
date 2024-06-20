package cn.xylose.mitemod.hwite.render;

import cn.xylose.mitemod.hwite.info.HwiteInfo;
import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import cn.xylose.mitemod.hwite.render.util.EnumRenderFlag;
import cn.xylose.mitemod.hwite.render.util.ScreenConstants;
import net.minecraft.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.render.HUDBackGroundRender.drawTooltipBackGround;

public class HUDRenderer {
    private static final RenderItemHwite itemRenderBlocks = new RenderItemHwite();

    public static void RenderHWITEHud(Gui gui, Minecraft mc, double zLevel) {
        ArrayList<String> list = new ArrayList<>();
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
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
        switch (enumRenderFlag) {
            case Small ->
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), HwiteInfo.blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(HwiteInfo.blockPosX, HwiteInfo.blockPosY, HwiteInfo.blockPosZ)), block_info_x, ScreenConstants.getBlockInfoYSmall());
            case Big ->
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), HwiteInfo.blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(HwiteInfo.blockPosX, HwiteInfo.blockPosY, HwiteInfo.blockPosZ)), block_info_x, ScreenConstants.getBlockInfoYBig());
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

        boolean smallFlag = false;
        boolean bigFlag = false;

        if (HwiteInfo.entityInfo != null) {
            list.add(HwiteInfo.infoMain);
            tryAddBreakProgress(list, breakProgress);
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
            }
        } else if (line2Empty) {
            if (!Objects.equals(HwiteInfo.break_info, "") && CanBreak.getBooleanValue()) {
                list.add(HwiteInfo.infoMain + "  " + HwiteInfo.break_info);
                tryAddBreakProgress(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
            } else {
                list.add(HwiteInfo.infoMain);
                tryAddBreakProgress(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
            }
            if (BlockRender.getBooleanValue() && HwiteInfo.blockInfo != null) {
                smallFlag = true;
            }
        } else if (line1IsABlank && line2IsABlank) {
            list.add(HwiteInfo.infoMain);
            if (breakProgress > 0) {
                list.add(String.format("%d", breakProgress) + "%");
            }
        } else {
            if (!Objects.equals(HwiteInfo.break_info, "") && CanBreak.getBooleanValue()) {
                list.add(HwiteInfo.infoMain + "  " + HwiteInfo.break_info);
                tryAddBreakProgress(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
//                } else if (isViewMode && ViewMode.getBooleanValue()) {
//                    list.add(info);
//                    list.add(modInfo);
            } else {
                list.add(HwiteInfo.infoMain);
                tryAddBreakProgress(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(HwiteInfo.info_line_1);
                list.add(HwiteInfo.info_line_2);
            }
            if (BlockRender.getBooleanValue() && HwiteInfo.blockInfo != null) {
                bigFlag = true;
            }
        }

        list.add(HwiteInfo.modInfo);

        if (smallFlag) {
            return EnumRenderFlag.Small;
        }
        if (bigFlag) {
            return EnumRenderFlag.Big;
        }
        return EnumRenderFlag.Nothing;
    }

    private static void tryAddBreakProgress(List<String> list, int breakProgress) {
        if (breakProgress > 0 && BreakProgress.getBooleanValue()) {
            list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
        }
    }
}
