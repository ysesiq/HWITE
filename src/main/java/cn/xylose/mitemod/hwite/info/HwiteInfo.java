package cn.xylose.mitemod.hwite.info;

import cn.xylose.mitemod.hwite.config.HwiteConfigs;
import net.minecraft.*;
import net.xiaoyu233.fml.api.block.IBlock;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;

public class HwiteInfo extends Gui {
    public static String infoMain;
    public static String info_line_1 = "";
    public static String info_line_2 = "";
    public static String info_line_3 = "";
    public static String break_info = "";
    public static String redstone_info = "";
    public static String growth_info = "";
    public static String spawner_info = "";
    public static EntityLivingBase entityInfo;
    public static Block blockInfo;
    public static ItemStack itemStackInfo;
    public static String modInfo = "";
    public static int blockPosX = 0;
    public static int blockPosY = 0;
    public static int blockPosZ = 0;

    static int mobSpawnerID = Block.mobSpawner.blockID;
    static int wheatCropID = Block.crops.blockID;
    static int melonStemID = Block.melonStem.blockID;
    static int pumpkinStemID = Block.pumpkinStem.blockID;
    static int carrotCropID = Block.carrot.blockID;
    static int potatoID = Block.potato.blockID;
    static int onionsID = Block.onions.blockID;
    static int netherStalkID = Block.netherStalk.blockID;
    static int leverID = Block.lever.blockID;
    static int repeaterIdle = Block.redstoneRepeaterIdle.blockID;
    static int repeaterActv = Block.redstoneRepeaterActive.blockID;
    static int comparatorIdl = Block.redstoneComparatorIdle.blockID;
    static int comparatorAct = Block.redstoneComparatorActive.blockID;
    static int redstone = Block.redstoneWire.blockID;
    static int skull = Block.skull.blockID;

    public static void updateInfoForRC(RaycastCollision rc, EntityPlayer player) {
        if (rc == null) {
            infoMain = "";
            info_line_1 = "";
            info_line_2 = "";
            break_info = "";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
            return;
        }
        entityInfo = null;
        if (rc.isEntity()) {
            updateRCEntity(rc);
        } else if (rc.isBlock()) {
            updateRCBlock(rc, player);
            updateGrowthInfo(rc, player);
            updateRedStoneInfo(rc, player);
            updateMobSpawnerInfo(rc, player);
        } else {
            info_line_1 = "";
            info_line_2 = "";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
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
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
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
            info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.health") + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth();
            info_line_2 = " ";
            break_info = "  ";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
        } else {
            info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.health") + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + I18n.getString("hwite.info.attack") + total_melee_damage;
            info_line_2 = " ";
            break_info = "  ";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
        }
    }

    private static void updateBlockInfo(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        blockInfo = block;
        itemStackInfo = block.createStackedBlock(metadata);
        float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int min_harvest_level = block.getMinHarvestLevel(metadata);
        info_line_1 = "";
        info_line_2 = "";
        growth_info = "";
        redstone_info = "";
        spawner_info = "";
        updateBreakInfo(rc, player);
        updateInfoMain(block.createStackedBlock(metadata), block, metadata);
        if (MITEDetailsInfo.getBooleanValue()) {
            updateInfoLine12(min_harvest_level, rc, block_hardness, player);
        }
    }

    private static void updateInfoLine12(int min_harvest_level, RaycastCollision rc, float block_hardness, EntityPlayer player) {
        if (min_harvest_level == 0) {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.hardness") + block_hardness;
            } else {
                info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.str_vs_block") + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        } else {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.harvest_level") + min_harvest_level;
            } else {
                info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.harvest_level") + min_harvest_level;
                info_line_2 = EnumChatFormatting.DARK_GRAY + I18n.getString("hwite.info.str_vs_block") + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        }
    }

    private static void updateBreakInfo(RaycastCollision rc, EntityPlayer player) {
        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
            break_info = EnumChatFormatting.DARK_RED + "✘" + EnumChatFormatting.WHITE;
        } else {
            break_info = EnumChatFormatting.DARK_GREEN + "✔" + EnumChatFormatting.WHITE;
        }
    }

    private static void updateGrowthInfo(RaycastCollision rc, EntityPlayer player) {
        int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        if (GrowthValue.getBooleanValue()) {
            if (rc.getBlockHitID() == wheatCropID || (Block.blocksList[blockID] instanceof BlockCrops) || Block.blocksList[blockID] instanceof BlockStem || blockID == netherStalkID) {
                int growthValue = (int)(blockID == netherStalkID ? metadata / 3.0F * 100F : (metadata & 7) / 7.0F * 100.0F);
                if (growthValue != 100.0D) {
                    growth_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.growth_value") + growthValue + "%";
                } else {
                    growth_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.growth_value_mature");
                }
            }
        } else {
            growth_info = "";
        }
    }

    private static void updateRedStoneInfo(RaycastCollision rc, EntityPlayer player) {
        int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        if (Redstone.getBooleanValue()) {
            if (blockID == leverID) {
                String leverOn = ((metadata & 0x8) == 0) ? EnumChatFormatting.RED + I18n.getString("hwite.info.off") : EnumChatFormatting.GREEN + I18n.getString("hwite.info.on");
                redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.state") + leverOn;
            }
            if ((Block.blocksList[blockID] instanceof BlockPressurePlate) || (Block.blocksList[blockID] instanceof BlockPressurePlateWeighted)) {
                String plateOn = ((metadata & 1) == 0) ? EnumChatFormatting.RED + I18n.getString("hwite.info.off") : EnumChatFormatting.GREEN + I18n.getString("hwite.info.on");
                redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.state") + plateOn;
            }
            if (blockID == repeaterIdle || blockID == repeaterActv) {
                int tick = (metadata >> 2) + 1;
                if (tick == 1) {
                    redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.delay") + tick + " tick";
                } else {
                    redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.delay") + tick + " ticks";
                }
            }
            if (blockID == comparatorIdl || blockID == comparatorAct) {
                String mode = ((metadata >> 2 & 0x1) == 0) ? I18n.getString("hwite.info.comparator") : I18n.getString("hwite.info.subtractor");
                redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.mode") + mode;
            }
            if (blockID == redstone) {
                redstone_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.power") + metadata;
            }
        } else {
            redstone_info = "";
        }
    }

    private static void updateMobSpawnerInfo(RaycastCollision rc, EntityPlayer player) {
        int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        TileEntity tileEntity = player.worldObj.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        if (HwiteConfigs.SpawnerType.getBooleanValue() && blockID == mobSpawnerID && tileEntity instanceof TileEntityMobSpawner) {
            spawner_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.type") + (((TileEntityMobSpawner) tileEntity).getSpawnerLogic().getEntityNameToSpawn());
        } else {
            spawner_info = "";
        }
    }

    private static void updateInfoMain(ItemStack itemStack, Block block, int metadata) {
        if (itemStack != null) {
            if (ShowIDAndMetadata.getBooleanValue()) {
                infoMain = itemStack.getDisplayName() + " (" + itemStack.itemID + ":" + metadata + ")";
            } else {
                infoMain = itemStack.getDisplayName();
            }
        } else {
            if (ShowIDAndMetadata.getBooleanValue()) {
                infoMain = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
            } else {
                infoMain = block.getLocalizedName();
            }
        }
    }

    private static void updateModInfoByBlock(Block block) {
        int id = block.blockID;
        if (id < 256) {
            if (id >= 164 && id < 170 || id >= 198 || id == 95) {
                modInfo = EnumChatFormatting.BLUE + "§o" + "MITE";
            } else if (id <= 163 || id >= 170 && id <= 174) {
                modInfo = EnumChatFormatting.BLUE + "§o" + "Minecraft";
            }
        } else {
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
//            modInfo = FishModLoader.getModContainer("modid").get().getMetadata().getName();
            modInfo = EnumChatFormatting.BLUE + "§o" + "Other Mod";
        }
    }


}
