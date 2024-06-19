package cn.xylose.mitemod.hwite;

import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import cn.xylose.mitemod.hwite.render.EnumRenderFlag;
import cn.xylose.mitemod.hwite.render.ScreenConstants;
import cn.xylose.mitemod.hwite.render.RenderItemHwite;
import net.minecraft.*;
import net.xiaoyu233.fml.api.block.IBlock;

import java.util.*;

import static cn.xylose.mitemod.hwite.render.HUDBackGroundRender.drawTooltipBackGround;
import static cn.xylose.mitemod.hwite.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.client.HwiteModClient.*;

public class HwiteInfo extends Gui {
    private static final RenderItemHwite itemRenderBlocks = new RenderItemHwite();

    public static void setHwiteInfoForSelectedObject(RaycastCollision rc, EntityPlayer player) {
        if (rc == null) {
            infoMain = "";
            info_line_1 = "";
            info_line_2 = "";
            break_info = "";
            return;
        }
        entityInfo = null;
        if (rc.isEntity()) {
            updateRCEntity(rc);
        } else if (rc.isBlock()) {
            updateRCBlock(rc, player);
        } else {
            info_line_1 = "";
            info_line_2 = "";
//                break_info = "";
        }
    }

    private static void updateRCEntity(RaycastCollision rc) {
        Entity entity = rc.getEntityHit();
        if (entity instanceof EntityLivingBase entityLivingBase) {
            updateModInfoByEntity(entityLivingBase);
            updateEntityLivingBase(entityLivingBase);
        } else {
            infoMain = entity.getTranslatedEntityName();
            info_line_1 = " ";
            info_line_2 = " ";
            break_info = " ";
        }
    }

    private static void updateRCBlock(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        blockPosX = rc.block_hit_x;
        blockPosY = rc.block_hit_y;
        blockPosZ = rc.block_hit_z;
        updateModInfoByBlock(block);
        updateBlock(rc, player);
    }

    private static void updateEntityLivingBase(EntityLivingBase entityLivingBase) {
        entityInfo = entityLivingBase;
        float total_melee_damage;
        if (entityLivingBase.isEntityPlayer()) {
            total_melee_damage = entityLivingBase.getAsPlayer().calcRawMeleeDamageVs((Entity) null, false, false);
        } else if (entityLivingBase.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
            total_melee_damage = (float) entityLivingBase.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
        } else {
            total_melee_damage = 0.0F;
        }
        infoMain = entityLivingBase.getEntityName();

        if (total_melee_damage == 0.0F) {
            info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth();
            info_line_2 = " ";
            break_info = "  ";
        } else {
            info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + " 伤害:" + total_melee_damage;
            info_line_2 = " ";
            break_info = "  ";
        }
    }

    private static void updateBlock(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        blockInfo = block;
        float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int min_harvest_level = block.getMinHarvestLevel(metadata);
        info_line_1 = "";
        info_line_2 = "";
        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
            break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
        } else {
            break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
        }
        updateInfoMain(block, metadata);
        if (shiftMoreInfo.getBooleanValue() && GuiScreen.isShiftKeyDown()) {
            updateInfoLine12MoreInfo(min_harvest_level, rc, block_hardness, player);
        } else {
            updateInfoLine12(min_harvest_level, rc, block_hardness, player);
        }
    }

    private static void updateInfoLine12MoreInfo(int min_harvest_level, RaycastCollision rc, float block_hardness, EntityPlayer player) {
        updateInfoLine12(min_harvest_level, rc, block_hardness, player);// TODO seems same now
    }

    private static void updateInfoLine12(int min_harvest_level, RaycastCollision rc, float block_hardness, EntityPlayer player) {
        if (min_harvest_level == 0) {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness;
            } else {
                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        } else {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
            } else {
                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                info_line_2 = EnumChatFormatting.DARK_GRAY + "挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        }
    }

    private static void updateInfoMain(Block block, int metadata) {
        if (ShowIDAndMetadata.getBooleanValue()) {
            infoMain = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
        } else {
            infoMain = block.getLocalizedName();
        }
    }

    private static void updateModInfoByBlock(Block block) {
        int id = block.blockID;
        if (id < 256) {
            if (id >= 164 && id < 170 || id >= 198 || id == 95) {
//                    modInfo = EnumChatFormatting.ITALIC + "§1" + "MITE";
                modInfo = EnumChatFormatting.BLUE + "§o" + "MITE";
            } else if (id <= 163 || id >= 170 && id <= 174) {
                modInfo = EnumChatFormatting.BLUE + "§o" + "Minecraft";
            }
        } else {
//                modInfo = EnumChatFormatting.ITALIC + "§1" + ModIdentification.getModName();
//                modInfo = FishModLoader.getModContainer("mite-ite").get().getMetadata().getName();
//                    modInfo = EnumChatFormatting.BLUE + "§o" + "Other Mod";
            modInfo = EnumChatFormatting.BLUE + "§o" + ((IBlock) block).getNamespace();
        }
    }

    private static void updateModInfoByEntity(EntityLivingBase entityLivingBase) {
        int id = EntityList.getEntityID(entityLivingBase);
        if (id <= 100 || id == 120 || id == 200) {
            modInfo = EnumChatFormatting.BLUE + "§o" + "Minecraft";
        } else if (id >= 512 && id <= 540) {
            modInfo = EnumChatFormatting.BLUE + "§o" + "MITE";
        } else {
            // TODO entity needs a name space
//                        modInfo = FishModLoader.getModContainer("modid").get().getMetadata().getName();
            modInfo = EnumChatFormatting.BLUE + "§o" + "Other Mod";
        }
    }


    public static void RenderHWITEHud(Gui gui, Minecraft mc, double zLevel) {
        ArrayList<String> list = new ArrayList<>();
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
        int block_info_x = ScreenConstants.getBlockInfoX(screenWidth);

        //draw model
        boolean mainInfoNotEmpty = !Objects.equals(infoMain, "");
        if (mainInfoNotEmpty && entityInfo != null && EntityRender.getBooleanValue()) {
            //x, y, size, ?, ?
            GuiInventory.func_110423_a(EntityInfoX.getIntegerValue(), EntityInfoY.getIntegerValue(), EntityInfoSize.getIntegerValue(), 0, 0, entityInfo);
        }

        //draw text and tooltip background
        EnumRenderFlag enumRenderFlag = addInfoToList(list);
        drawTooltipBackGround(list, ScreenConstants.getHudX(screenWidth), ScreenConstants.getHudY(), false, mc, zLevel);
        switch (enumRenderFlag) {
            case Small ->
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(blockPosX, blockPosY, blockPosZ)), block_info_x, ScreenConstants.getBlockInfoYSmall());
            case Big ->
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(blockPosX, blockPosY, blockPosZ)), block_info_x, ScreenConstants.getBlockInfoYBig());
        }
    }

    private static EnumRenderFlag addInfoToList(List<String> list) {
        int breakProgress = (int) (((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress() * 100);
        boolean mainInfoNotEmpty = !Objects.equals(infoMain, "");
        boolean line2Empty = Objects.equals(info_line_2, "");
        boolean line1Empty = Objects.equals(info_line_1, "");
        boolean line1IsABlank = Objects.equals(info_line_1, " ");
        boolean line2IsABlank = Objects.equals(info_line_2, " ");

        if (mainInfoNotEmpty && entityInfo != null) {
            list.add(infoMain);
            tryAddBreakProgress(list, breakProgress);
            if (!line1Empty) {
                list.add(info_line_1);
            }
            list.add(modInfo);
        } else if (line2Empty && mainInfoNotEmpty) {
            if (!Objects.equals(break_info, "") && CanBreak.getBooleanValue()) {
                list.add(infoMain + "  " + break_info);
                tryAddBreakProgress(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
            } else {
                list.add(infoMain);
                tryAddBreakProgress(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(info_line_1);
            }
            list.add(modInfo);
            if (BlockRender.getBooleanValue() && blockInfo != null) {
                return EnumRenderFlag.Small;
            }
        } else if (line1IsABlank && line2IsABlank && mainInfoNotEmpty) {
            list.add(infoMain);
            if (breakProgress > 0) {
                list.add(String.format("%d", breakProgress) + "%");
            }
            list.add(modInfo);
        } else if (mainInfoNotEmpty) {
            if (!Objects.equals(break_info, "") && CanBreak.getBooleanValue()) {
                list.add(infoMain + "  " + break_info);
                tryAddBreakProgress(list, breakProgress);
                //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(screenWidth / 2 + 10, screenHeight - (screenHeight - 30), 0, 0, 16, 16);
//                } else if (isViewMode && ViewMode.getBooleanValue()) {
//                    list.add(info);
//                    list.add(modInfo);
            } else {
                list.add(infoMain);
                tryAddBreakProgress(list, breakProgress);
            }
            if (!line1Empty) {
                list.add(info_line_1);
                list.add(info_line_2);
            }
            list.add(modInfo);
            if (BlockRender.getBooleanValue() && blockInfo != null) {
                return EnumRenderFlag.Big;
            }
        }
        return EnumRenderFlag.Nothing;
    }

    private static void tryAddBreakProgress(List<String> list, int breakProgress) {
        if (breakProgress > 0 && BreakProgress.getBooleanValue()) {
            list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
        }
    }


}
