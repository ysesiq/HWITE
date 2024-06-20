package cn.xylose.mitemod.hwite.info;

import net.minecraft.*;
import net.xiaoyu233.fml.api.block.IBlock;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;

public class HwiteInfo extends Gui {
    public static String infoMain;
    public static String info_line_1 = "";
    public static String info_line_2 = "";
    public static String info_line_3 = "";
    public static String break_info = "";
    public static EntityLivingBase entityInfo;
    public static Block blockInfo;
    public static String modInfo = "";
    public static int blockPosX = 0;
    public static int blockPosY = 0;
    public static int blockPosZ = 0;

    public static void updateInfoForRC(RaycastCollision rc, EntityPlayer player) {
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
            updateEntityLivingBaseInfo(entityLivingBase);
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
        updateBlockInfo(rc, player);
    }

    private static void updateEntityLivingBaseInfo(EntityLivingBase entityLivingBase) {
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

    private static void updateBlockInfo(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        blockInfo = block;
        float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int min_harvest_level = block.getMinHarvestLevel(metadata);
        info_line_1 = "";
        info_line_2 = "";
        updateBreakInfo(rc, player);
        updateInfoMain(block, metadata);
        if (shiftMoreInfo.getBooleanValue() && GuiScreen.isShiftKeyDown()) {
            updateInfoLine12Detail(min_harvest_level, rc, block_hardness, player);
        } else {
            updateInfoLine12(min_harvest_level, rc, block_hardness, player);
        }
    }

    private static void updateInfoLine12Detail(int min_harvest_level, RaycastCollision rc, float block_hardness, EntityPlayer player) {
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

    private static void updateBreakInfo(RaycastCollision rc, EntityPlayer player) {
        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
            break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
        } else {
            break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
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


}
