package cn.xylose.mitemod.hwite.info;

import cn.xylose.mitemod.hwite.config.HwiteConfigs;
import net.minecraft.*;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.api.block.IBlock;
import net.xiaoyu233.fml.api.entity.IEntity;

import java.text.DecimalFormat;

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
    public static String devInfo = "";
    public static String unlocalizedNameInfo = "";
    public static String hiwlaInfo = "";
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
    static int runestoneAdamantiumID = Block.runestoneAdamantium.blockID;

    public static void updateInfoForRC(RaycastCollision rc, EntityPlayer player) {
        if (rc == null) {
            infoMain = "";
            info_line_1 = "";
            info_line_2 = "";
            break_info = "";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
            return;
        }
        entityInfo = null;
        if (rc.isEntity()) {
            updateRCEntity(rc);
            updateDevInfoInfo(rc, player);
            updateHiwlaExtraInfoInfo(rc, player);
        } else if (rc.isBlock()) {
            updateRCBlock(rc, player);
            updateGrowthInfo(rc, player);
            updateRedStoneInfo(rc, player);
            updateMobSpawnerInfo(rc, player);
            updateDevInfoInfo(rc, player);
        } else {
            info_line_1 = "";
            info_line_2 = "";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
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
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
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
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
        } else {
            info_line_1 = EnumChatFormatting.GRAY + I18n.getString("hwite.info.health") + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + I18n.getString("hwite.info.attack") + total_melee_damage;
            info_line_2 = " ";
            break_info = "  ";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
        }
    }

    private static void updateBlockInfo(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        blockInfo = block;
        if (block != null) {
            itemStackInfo = block.createStackedBlock(metadata);
            float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            int min_harvest_level = block.getMinHarvestLevel(metadata);
            info_line_1 = "";
            info_line_2 = "";
            growth_info = "";
            redstone_info = "";
            spawner_info = "";
            devInfo = "";
            hiwlaInfo = "";
            unlocalizedNameInfo = "";
            updateBreakInfo(rc, player);
            updateInfoMain(block.createStackedBlock(metadata), block, metadata);
            if (MITEDetailsInfo.getBooleanValue()) {
                updateInfoLine12(min_harvest_level, rc, block_hardness, player);
            }
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
            break_info = EnumChatFormatting.DARK_RED + CannotBreakString.getStringValue() + EnumChatFormatting.WHITE;
        } else {
            break_info = EnumChatFormatting.DARK_GREEN + CanBreakString.getStringValue() + EnumChatFormatting.WHITE;
        }
    }

    private static void updateGrowthInfo(RaycastCollision rc, EntityPlayer player) {
        int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        if (GrowthValue.getBooleanValue()) {
            if (rc.getBlockHitID() == wheatCropID || (Block.blocksList[blockID] instanceof BlockCrops) || Block.blocksList[blockID] instanceof BlockStem || blockID == netherStalkID) {
                int growthValue = (int) (blockID == netherStalkID ? metadata / 3.0F * 100F : (metadata & 7) / 7.0F * 100.0F);
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
        if (SpawnerType.getBooleanValue() && blockID == mobSpawnerID && tileEntity instanceof TileEntityMobSpawner) {
            spawner_info = EnumChatFormatting.GRAY + I18n.getString("hwite.info.type") + (((TileEntityMobSpawner) tileEntity).getSpawnerLogic().getEntityNameToSpawn());
        } else {
            spawner_info = "";
        }
    }

    private static void updateDevInfoInfo(RaycastCollision rc, EntityPlayer player) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        double distance = Double.parseDouble(decimalFormat.format(rc.getDistanceFromOriginToCollisionPoint()));
        if (rc != null) {
            if (rc.isBlock()) {
                EnumDirection direction = rc.getBlockHit().getDirectionFacing(rc.block_hit_metadata);
                if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    devInfo = EnumChatFormatting.GRAY + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " [" + distance + "]" + " {" + direction + "}";
                } else if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue()) {
                    devInfo = EnumChatFormatting.GRAY + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " [" + distance + "]";
                } else if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    devInfo = EnumChatFormatting.GRAY + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " {" + direction + "}";
                } else if (ShowDistance.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    devInfo = EnumChatFormatting.GRAY + "[" + distance + "]" + " {" + direction + "}";
                } else if (ShowBlockOrEntityCoord.getBooleanValue()) {
                    devInfo = EnumChatFormatting.GRAY + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z;
                } else if (ShowDistance.getBooleanValue()) {
                    devInfo = EnumChatFormatting.GRAY + "[" + distance + "]";
                } else if (ShowDirection.getBooleanValue() && direction != null) {
                    devInfo = EnumChatFormatting.GRAY + "{" + direction + "}";
                } else {
                    devInfo = "";
                }
                if (ShowBlockUnlocalizedName.getBooleanValue()) {
                    unlocalizedNameInfo = EnumChatFormatting.GRAY + rc.getBlockHit().getUnlocalizedName();
                } else {
                    unlocalizedNameInfo = "";
                }
            } else if (rc.isEntity()) {
                if (rc.getEntityHit() instanceof EntityLivingBase) {
                    if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue()) {
                        devInfo = EnumChatFormatting.GRAY + String.valueOf((int) rc.getEntityHit().posX) + " " + (int) rc.getEntityHit().posY + " " + (int) rc.getEntityHit().posZ + " [" + distance + "]";
                    } else if (ShowBlockOrEntityCoord.getBooleanValue()) {
                        devInfo = EnumChatFormatting.GRAY + String.valueOf((int) rc.getEntityHit().posX) + " " + (int) rc.getEntityHit().posY + " " + (int) rc.getEntityHit().posZ;
                    } else if (ShowDistance.getBooleanValue()) {
                        devInfo = "[" + distance + "]";
                    } else {
                        devInfo = "";
                    }
                }
            }
        }
    }

    private static void updateHiwlaExtraInfoInfo(RaycastCollision rc, EntityPlayer player) {
        TileEntity tileEntity = rc.world.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
        if (rc != null) {
            if (rc.isBlock()) {
//            if (tileEntity instanceof TileEntityBeacon beacon) {
//                hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.beacon.level") + beacon.getLevels();
//            } else {
//                hiwlaInfo = "";
//            }
//            if (rc.getBlockHit() instanceof BlockFurnace && tileEntity instanceof TileEntityFurnace) {
////            hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.furnace.burn_time") + (((TileEntityFurnace) tileEntity).getBurnTimeRemainingScaled(1));
//                hiwlaInfo = "这是熔炉";
//            } else {
//                hiwlaInfo = "";
//            }
            } else if (rc.isEntity()) {
                if (rc.getEntityHit() instanceof EntityLivingBase living) {
                    if (LivingProtection.getBooleanValue() && living.getTotalProtection(DamageSource.causeMobDamage((EntityLivingBase) null)) > 0) {
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.protection") + decimalFormat.format((living.getTotalProtection(DamageSource.causeMobDamage((EntityLivingBase) null))));
                    } else {
                        hiwlaInfo = "";
                    }
                }
//            if (rc.getEntityHit() instanceof EntityHorse horse) {
//                if (HorseJump.getBooleanValue()) {
//                    hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.horse_jump") + horse.getOwnerName();
//                } else {
//                    hiwlaInfo = "";
//                }
//            }
                if (rc.getEntityHit() instanceof EntityAnimal animal) {
                    if (AnimalGrowthTime.getBooleanValue() && animal.getGrowingAge() < 0) {
                        hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.animal_growing_time") + Math.abs(animal.getGrowingAge()) / 20;
                    } else {
                        hiwlaInfo = "";
                    }
                }
                if (rc.getEntityHit() instanceof EntityVillager villager) {
                    String villagerProfession = getProfession(villager);
                    if (VillagerProfession.getBooleanValue() && villager.getProfession() > -1) {
                        hiwlaInfo = EnumChatFormatting.GRAY + I18n.getString("hiwla.info.profession") + villagerProfession;
                    } else {
                        hiwlaInfo = "";
                    }
                }
            }
        }
    }


    private static String getProfession(EntityVillager villager) {
        String villagerProfession = I18n.getString("hiwla.info.profession.villager");
        if (villager.getProfession() == 5) {
            villagerProfession = I18n.getString("hiwla.info.profession.villager");
        } else if (villager.getProfession() == 0) {
            villagerProfession = I18n.getString("hiwla.info.profession.farmer");
        } else if (villager.getProfession() == 1) {
            villagerProfession = I18n.getString("hiwla.info.profession.librarian");
        } else if (villager.getProfession() == 2) {
            villagerProfession = I18n.getString("hiwla.info.profession.priest");
        } else if (villager.getProfession() == 3) {
            villagerProfession = I18n.getString("hiwla.info.profession.smith");
        } else if (villager.getProfession() == 4) {
            villagerProfession = I18n.getString("hiwla.info.profession.butcher");
        }
        return villagerProfession;
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
                modInfo = "§9§o" + "MITE";
            } else if (id <= 163 || id >= 170 && id <= 174) {
                modInfo = "§9§o" + "Minecraft";
            }
        } else {
            modInfo = "§9§o" + ((IBlock) block).getNamespace();
        }
    }

    private static void updateModInfoByEntity(EntityLivingBase entityLivingBase) {
        int id = EntityList.getEntityID(entityLivingBase);
        if (id <= 100 || id == 120 || id == 200) {
            modInfo = "§9§o" + "Minecraft";
        } else if (id >= 512 && id <= 540) {
            modInfo = "§9§o" + "MITE";
        } else {
            modInfo = "§9§o" + ((IEntity) entityLivingBase).getNamespace();
        }
    }

//    private static void hotKeyPress(RaycastCollision rc) {
//        if(FishModLoader.hasMod("emi")) {
//        RecipeHotkey.setHotKeyPressCallBack(minecraft -> {
//            EmiApi.displayRecipes(rc.getBlockHit());
//            if (rc != null) {
//                EmiApi.focusRecipe(stack.getRecipeContext());
//            }
//            return true;
//        } );
}
