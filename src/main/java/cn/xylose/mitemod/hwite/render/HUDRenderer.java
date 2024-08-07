package cn.xylose.mitemod.hwite.render;

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
    static Minecraft mc = Minecraft.getMinecraft();

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
                    renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, block_info_x, (int) (HUDBackGroundRender.stringHeight / 3.3));
                    renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, itemStack, block_info_x, (int) (HUDBackGroundRender.stringHeight / 3.3));
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

        list.add(HwiteInfo.updateModInfo(mc.objectMouseOver));

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
        tryAddDevValue(list);
        tryAddHiwlaValue(list);
        tryAddMITEDetailsInfo(list);
        tryAddMITEDetailsInfo1(list);
        tryAddFurnaceInputItemInfo(list);
        tryAddFurnaceOutputItemInfo(list);
        tryAddFurnaceFuelItemInfo(list);
        tryAddHorseInfo(list);
    }

    private static void tryAddBreakProgress(List<String> list, int breakProgress) {
        if (breakProgress > 0 && BreakProgress.getBooleanValue()) {
            list.add(String.format(EnumChatFormatting.DARK_GRAY + I18n.getString("hwite.info.breakProgress") + "%d", breakProgress) + "%");
        }
    }

    private static void tryAddHorseInfo(List<String> list) {
        if (HorseInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateHorseInfo(mc.objectMouseOver), "")) {
            list.add(HwiteInfo.updateHorseInfo(mc.objectMouseOver));
        }
    }

    private static void tryAddFurnaceFuelItemInfo(List<String> list) {
        if (FurnaceInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateFurnaceFuelItemInfo(mc.objectMouseOver), "")) {
            list.add(HwiteInfo.updateFurnaceFuelItemInfo(mc.objectMouseOver));
        }
    }

    private static void tryAddFurnaceOutputItemInfo(List<String> list) {
        if (FurnaceInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateFurnaceOutputItemInfo(mc.objectMouseOver), "")) {
            list.add(HwiteInfo.updateFurnaceOutputItemInfo(mc.objectMouseOver));
        }
    }

    private static void tryAddFurnaceInputItemInfo(List<String> list) {
        if (FurnaceInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateFurnaceInputItemInfo(mc.objectMouseOver), "")) {
            list.add(HwiteInfo.updateFurnaceInputItemInfo(mc.objectMouseOver));
        }
    }

    private static void tryAddMITEDetailsInfo(List<String> list) {
        if (MITEDetailsInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateMITEDetailsInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateMITEDetailsInfo(mc.objectMouseOver, mc.thePlayer));
        }
    }

    private static void tryAddMITEDetailsInfo1(List<String> list) {
        if (MITEDetailsInfo.getBooleanValue() && !Objects.equals(HwiteInfo.updateInfoLine2(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateInfoLine2(mc.objectMouseOver, mc.thePlayer));
        }
    }

    private static void tryAddGrowthValue(List<String> list) {
        if (GrowthValue.getBooleanValue() && !Objects.equals(HwiteInfo.updateGrowthInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateGrowthInfo(mc.objectMouseOver, mc.thePlayer));
        }
    }

    private static void tryAddRedstoneValue(List<String> list) {
        if (Redstone.getBooleanValue() && !Objects.equals(HwiteInfo.updateRedStoneInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateRedStoneInfo(mc.objectMouseOver, mc.thePlayer));
        }
    }

    private static void tryAddSpawnerValue(List<String> list) {
        if (SpawnerType.getBooleanValue() && !Objects.equals(HwiteInfo.updateMobSpawnerInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateMobSpawnerInfo(mc.objectMouseOver, mc.thePlayer));
        }
    }

    private static void tryAddDevValue(List<String> list) {
        if (!Objects.equals(HwiteInfo.updateDevInfoInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateDevInfoInfo(mc.objectMouseOver, mc.thePlayer));
        }
        if (!Objects.equals(HwiteInfo.unlocalizedNameInfo, "")) {
            list.add(HwiteInfo.unlocalizedNameInfo);
        }
    }

    private static void tryAddHiwlaValue(List<String> list) {
        if (!Objects.equals(HwiteInfo.updateHiwlaExtraInfo(mc.objectMouseOver, mc.thePlayer), "")) {
            list.add(HwiteInfo.updateHiwlaExtraInfo(mc.objectMouseOver, mc.thePlayer));
        }
    }

}
