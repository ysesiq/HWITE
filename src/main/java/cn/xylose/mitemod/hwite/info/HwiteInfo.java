package cn.xylose.mitemod.hwite.info;

import emi.dev.emi.emi.api.EmiApi;
import emi.dev.emi.emi.api.stack.EmiStack;
import net.minecraft.*;
import net.xiaoyu233.fml.api.block.IBlock;
import net.xiaoyu233.fml.api.entity.IEntity;

import java.text.DecimalFormat;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;

public class HwiteInfo extends Gui {
    static Minecraft mc = Minecraft.getMinecraft();
    static EntityPlayer player = mc.thePlayer;
    public static EnumChatFormatting gray = EnumChatFormatting.GRAY;
    public static String infoMain;
    public static String info_line_1 = "";
    public static String info_line_2 = "";
    public static String break_info = "";
    public static Entity entityInfo;
    public static Block blockInfo;
    public static ItemStack itemStackInfo;
    public static String unlocalizedNameInfo = "";
    public static int blockPosX = 0;
    public static int blockPosY = 0;
    public static int blockPosZ = 0;
    public static boolean hasIcon;

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
            unlocalizedNameInfo = "";
            return;
        }
        entityInfo = null;
        if (rc.isEntity()) {
            updateRCEntity(rc);
            updateDevInfoInfo(rc, player);
            updateHiwlaExtraInfo(rc, player);
        } else if (rc.isBlock()) {
            updateRCBlock(rc, player);
            updateGrowthInfo(rc, player);
            updateRedStoneInfo(rc, player);
            updateMobSpawnerInfo(rc, player);
            updateDevInfoInfo(rc, player);
            hotKeyPress(rc);
        } else {
            info_line_1 = "";
            info_line_2 = "";
            unlocalizedNameInfo = "";
        }
    }

    private static void updateRCEntity(RaycastCollision rc) {
        Entity entity = rc.getEntityHit();
        if (entity instanceof EntityLivingBase entityLivingBase) {
            updateEntityLivingBaseInfo(entityLivingBase);
        } else {
            if (ShowIDAndMetadata.getBooleanValue()) {
                infoMain = entity.getTranslatedEntityName() + " (" + EntityList.getEntityID(entity) + ")";
            } else {
                infoMain = entity.getTranslatedEntityName();
            }
            info_line_1 = " ";
            info_line_2 = " ";
            break_info = " ";
            unlocalizedNameInfo = "";
        }
        hasIcon = false;
    }

    private static void updateRCBlock(RaycastCollision rc, EntityPlayer player) {
        Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
        blockPosX = rc.block_hit_x;
        blockPosY = rc.block_hit_y;
        blockPosZ = rc.block_hit_z;
        updateBlockInfo(rc, player);
        hasIcon = true;
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
        if (ShowIDAndMetadata.getBooleanValue()) {
            infoMain = entityLivingBase.getEntityName() + " (" + EntityList.getEntityID(entityLivingBase) + ")";
        } else {
            infoMain = entityLivingBase.getEntityName();
        }

        if (total_melee_damage == 0.0F) {
            info_line_1 = gray + I18n.getString("hwite.info.health") + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth();
            info_line_2 = " ";
            break_info = "  ";
            unlocalizedNameInfo = "";
        } else {
            info_line_1 = gray + I18n.getString("hwite.info.health") + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + I18n.getString("hwite.info.attack") + total_melee_damage;
            info_line_2 = " ";
            break_info = "  ";
            unlocalizedNameInfo = "";
        }
    }

    private static void updateBlockInfo(RaycastCollision rc, EntityPlayer player) {
        if (rc != null) {
            Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
            int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            blockInfo = block;
            if (block != null) {
                itemStackInfo = block.createStackedBlock(metadata);
                info_line_1 = "";
                info_line_2 = "";
                unlocalizedNameInfo = "";
                updateBreakInfo(rc, player);
                updateInfoMain(block.createStackedBlock(metadata), block, metadata);
            }
        }
    }

    public static String updateInfoLine1(int min_harvest_level, RaycastCollision rc, float block_hardness, EntityPlayer player) {
        String info1;
        if (min_harvest_level == 0) {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                return info1 = gray + I18n.getString("hwite.info.hardness") + block_hardness;
            } else {
                return info1 = gray + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.str_vs_block") + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        } else {
            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                return info1 = gray + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.harvest_level") + min_harvest_level;
            } else {
                return info1 = gray + I18n.getString("hwite.info.hardness") + block_hardness + I18n.getString("hwite.info.harvest_level") + min_harvest_level;
            }
        }
    }

    public static String updateInfoLine2(RaycastCollision rc, EntityPlayer player) {
        String info2;
        if (rc != null && rc.isBlock()) {
            Block block = rc.getBlockHit();
            int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            int min_harvest_level = block.getMinHarvestLevel(metadata);
            if (min_harvest_level != 0 && player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) > 0.0) {
                return info2 = EnumChatFormatting.DARK_GRAY + I18n.getString("hwite.info.str_vs_block") + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
            }
        }
        return "";
    }


    public static String updateMITEDetailsInfo(RaycastCollision rc, EntityPlayer player) {
        if (rc != null && rc.isBlock()) {
            Block block = rc.getBlockHit();
            int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            int min_harvest_level = block.getMinHarvestLevel(metadata);
            if (MITEDetailsInfo.getBooleanValue()) {
                return updateInfoLine1(min_harvest_level, rc, block_hardness, player);
//                return updateInfoLine2(min_harvest_level, rc, block_hardness, player);
            }
        }
        return "";
    }

    private static void updateBreakInfo(RaycastCollision rc, EntityPlayer player) {
        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
            break_info = EnumChatFormatting.DARK_RED + CannotBreakString.getStringValue() + EnumChatFormatting.WHITE;
        } else {
            break_info = EnumChatFormatting.DARK_GREEN + CanBreakString.getStringValue() + EnumChatFormatting.WHITE;
        }
    }

    public static String updateGrowthInfo(RaycastCollision rc, EntityPlayer player) {
        String growthInfo;
        if (rc != null) {
            int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (GrowthValue.getBooleanValue()) {
                if (rc.getBlockHitID() == wheatCropID || (Block.blocksList[blockID] instanceof BlockCrops) || Block.blocksList[blockID] instanceof BlockStem || blockID == netherStalkID) {
                    int growthValue = (int) (blockID == netherStalkID ? metadata / 3.0F * 100F : (metadata & 7) / 7.0F * 100.0F);
                    if (growthValue != 100.0D) {
                        return growthInfo = gray + I18n.getString("hwite.info.growth_value") + growthValue + "%";
                    } else {
                        return growthInfo = gray + I18n.getString("hwite.info.growth_value_mature");
                    }
                }
            }
        }
        return "";
    }

    public static String updateRedStoneInfo(RaycastCollision rc, EntityPlayer player) {
        String redstoneInfo;
        if (rc != null) {
            int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (Redstone.getBooleanValue()) {
                if (blockID == leverID) {
                    String leverOn = ((metadata & 0x8) == 0) ? EnumChatFormatting.RED + I18n.getString("hwite.info.off") : EnumChatFormatting.GREEN + I18n.getString("hwite.info.on");
                    return redstoneInfo = gray + I18n.getString("hwite.info.state") + leverOn;
                }
                if ((Block.blocksList[blockID] instanceof BlockPressurePlate) || (Block.blocksList[blockID] instanceof BlockPressurePlateWeighted)) {
                    String plateOn = ((metadata & 1) == 0) ? EnumChatFormatting.RED + I18n.getString("hwite.info.off") : EnumChatFormatting.GREEN + I18n.getString("hwite.info.on");
                    return redstoneInfo = gray + I18n.getString("hwite.info.state") + plateOn;
                }
                if (blockID == repeaterIdle || blockID == repeaterActv) {
                    int tick = (metadata >> 2) + 1;
                    if (tick == 1) {
                        return redstoneInfo = gray + I18n.getString("hwite.info.delay") + tick + " tick";
                    } else {
                        return redstoneInfo = gray + I18n.getString("hwite.info.delay") + tick + " ticks";
                    }
                }
                if (blockID == comparatorIdl || blockID == comparatorAct) {
                    String mode = ((metadata >> 2 & 0x1) == 0) ? I18n.getString("hwite.info.comparator") : I18n.getString("hwite.info.subtractor");
                    return redstoneInfo = gray + I18n.getString("hwite.info.mode") + mode;
                }
                if (blockID == redstone) {
                    return redstoneInfo = gray + I18n.getString("hwite.info.power") + metadata;
                }
            }
        }
        return "";
    }

    public static String updateMobSpawnerInfo(RaycastCollision rc, EntityPlayer player) {
        String spawnerInfo;
        if (rc != null) {
            int blockID = player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            TileEntity tileEntity = player.worldObj.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (SpawnerType.getBooleanValue() && blockID == mobSpawnerID && tileEntity instanceof TileEntityMobSpawner) {
                return spawnerInfo = gray + I18n.getString("hwite.info.type") + (((TileEntityMobSpawner) tileEntity).getSpawnerLogic().getEntityNameToSpawn());
            }
        }
        return "";
    }

    public static String updateDevInfoInfo(RaycastCollision rc, EntityPlayer player) {
        String devInfo;
        String unlocalizedNameInfo;
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        if (rc != null) {
            double distance = Double.parseDouble(decimalFormat.format(rc.getDistanceFromOriginToCollisionPoint()));
            if (rc.isBlock()) {
                EnumDirection direction = rc.getBlockHit().getDirectionFacing(rc.block_hit_metadata);
                if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    return devInfo = gray + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " [" + distance + "]" + " {" + direction + "}";
                } else if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue()) {
                    return devInfo = gray + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " [" + distance + "]";
                } else if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    return devInfo = gray + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z + " {" + direction + "}";
                } else if (ShowDistance.getBooleanValue() && ShowDirection.getBooleanValue() && direction != null) {
                    return devInfo = gray + "[" + distance + "]" + " {" + direction + "}";
                } else if (ShowBlockOrEntityCoord.getBooleanValue()) {
                    return devInfo = gray + String.valueOf(rc.block_hit_x) + " " + rc.block_hit_y + " " + rc.block_hit_z;
                } else if (ShowDistance.getBooleanValue()) {
                    return devInfo = gray + "[" + distance + "]";
                } else if (ShowDirection.getBooleanValue() && direction != null) {
                    return devInfo = gray + "{" + direction + "}";
                }
                if (ShowBlockUnlocalizedName.getBooleanValue()) {
                    return unlocalizedNameInfo = gray + rc.getBlockHit().getUnlocalizedName();
                }
            } else if (rc.isEntity()) {
                if (rc.getEntityHit() instanceof EntityLivingBase) {
                    if (ShowBlockOrEntityCoord.getBooleanValue() && ShowDistance.getBooleanValue()) {
                        return devInfo = gray + String.valueOf((int) rc.getEntityHit().posX) + " " + (int) rc.getEntityHit().posY + " " + (int) rc.getEntityHit().posZ + " [" + distance + "]";
                    } else if (ShowBlockOrEntityCoord.getBooleanValue()) {
                        return devInfo = gray + String.valueOf((int) rc.getEntityHit().posX) + " " + (int) rc.getEntityHit().posY + " " + (int) rc.getEntityHit().posZ;
                    } else if (ShowDistance.getBooleanValue()) {
                        return devInfo = gray + "[" + distance + "]";
                    }
                }
            }
        }
        return "";
    }


    public static String updateFurnaceInputItemInfo(RaycastCollision rc) {
        String inputItem;
        if (rc != null && rc.isBlock()) {
            TileEntity tileEntity = rc.world.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (rc.getBlockHit() instanceof BlockFurnace && tileEntity instanceof TileEntityFurnace tileEntityFurnace && tileEntityFurnace.getInputItemStack() != null && GuiScreen.isShiftKeyDown()) {
                return inputItem = gray + I18n.getString("hiwla.info.furnace.input") + tileEntityFurnace.getInputItemStack().getDisplayName() + "x" + tileEntityFurnace.getInputItemStack().stackSize;
            }
        }
        return "";
    }

    public static String updateFurnaceOutputItemInfo(RaycastCollision rc) {
        String outputItem;
        if (rc != null && rc.isBlock()) {
            TileEntity tileEntity = rc.world.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (rc.getBlockHit() instanceof BlockFurnace && tileEntity instanceof TileEntityFurnace tileEntityFurnace && tileEntityFurnace.getOutputItemStack() != null && GuiScreen.isShiftKeyDown()) {
                return outputItem = gray + I18n.getString("hiwla.info.furnace.output") + tileEntityFurnace.getOutputItemStack().getDisplayName() + "x" + tileEntityFurnace.getOutputItemStack().stackSize;
            }
        }
        return "";
    }

    public static String updateFurnaceFuelItemInfo(RaycastCollision rc) {
        String fuelItem;
        if (rc != null && rc.isBlock()) {
            TileEntity tileEntity = rc.world.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
            if (rc.getBlockHit() instanceof BlockFurnace && tileEntity instanceof TileEntityFurnace tileEntityFurnace && tileEntityFurnace.getFuelItemStack() != null && GuiScreen.isShiftKeyDown()) {
                return fuelItem = gray + I18n.getString("hiwla.info.furnace.fuel") + tileEntityFurnace.getFuelItemStack().getDisplayName() + "x" + tileEntityFurnace.getFuelItemStack().stackSize;
            }
        }
        return "";
    }

    public static String updateHorseInfo(RaycastCollision rc) {
        String horseInfo;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (rc != null && rc.isEntity()) {
            if (rc.getEntityHit() instanceof EntityHorse horse && HorseInfo.getBooleanValue()) {
                return horseInfo = gray + I18n.getString("hiwla.info.horse.jump") + decimalFormat.format(1 + horse.getHorseJumpStrength()) + " " + I18n.getString("hiwla.info.horse.speed") + decimalFormat.format(horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
            }
        }
        return "";
    }

    public static String updateHiwlaExtraInfo(RaycastCollision rc, EntityPlayer player) {
        String hiwlaInfo;
        if (rc != null) {
            if (rc.isBlock()) {
                TileEntity tileEntity = rc.world.getBlockTileEntity(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                if (FurnaceInfo.getBooleanValue() && rc.getBlockHit() instanceof BlockFurnace furnace && tileEntity instanceof TileEntityFurnace tileEntityFurnace && tileEntityFurnace.furnaceBurnTime != 0 && tileEntityFurnace.getFuelItemStack() != null) {
                    if (tileEntityFurnace.isBurning() && furnace.isActive)
                        return hiwlaInfo = gray + I18n.getString("hiwla.info.furnace.burn_time") + tileEntityFurnace.furnaceBurnTime / 20 + I18n.getString("hwite.info.second") + I18n.getString("hiwla.info.furnace.heat_level") + tileEntityFurnace.heat_level;
                }
                if (BeaconLevel.getBooleanValue() && rc.getBlockHit() instanceof BlockBeacon && tileEntity instanceof TileEntityBeacon tileEntityBeacon && tileEntityBeacon.getLevels() > -1) {
                    return hiwlaInfo = gray + I18n.getString("hiwla.info.beacon.level") + tileEntityBeacon.getLevels();
                }
            } else if (rc.isEntity()) {
                if (rc.getEntityHit() instanceof EntityLivingBase living) {
                    if (LivingProtection.getBooleanValue() && living.getTotalProtection(DamageSource.causeMobDamage((EntityLivingBase) null)) > 0) {
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        return hiwlaInfo = gray + I18n.getString("hiwla.info.protection") + decimalFormat.format((living.getTotalProtection(DamageSource.causeMobDamage((EntityLivingBase) null))));
                    }
                    if (rc.getEntityHit() instanceof EntityAnimal animal) {
                        if (AnimalGrowthTime.getBooleanValue() && animal.getGrowingAge() < 0) {
                            return hiwlaInfo = gray + I18n.getString("hiwla.info.animal_growing_time") + Math.abs(animal.getGrowingAge()) / 20;
                        }
                    }
                    if (rc.getEntityHit() instanceof EntityVillager villager) {
                        String villagerProfession = getProfession(villager);
                        if (VillagerProfession.getBooleanValue() && villager.getProfession() > -1) {
                            return hiwlaInfo = gray + I18n.getString("hiwla.info.profession") + villagerProfession;
                        }
                    }
                }
            }
        }
        return "";
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

    private static String updateModInfoByBlock(Block block) {
        String modInfo;
        int id = block.blockID;
        if (id < 256) {
            if (id >= 164 && id < 170 || id >= 198 || id == 95) {
                return modInfo = "§9§o" + "MITE";
            } else if (id <= 163 || id >= 170 && id <= 174) {
                return modInfo = "§9§o" + "Minecraft";
            }
        } else {
            return modInfo = "§9§o" + ((IBlock) block).getNamespace();
        }
        return modInfo = "§9§o" + ((IBlock) block).getNamespace();
    }

    private static String updateModInfoByEntity(Entity entity) {
        String modInfo;
        int id = EntityList.getEntityID(entity);
        if (id <= 100 || id == 120 || id == 200) {
            return modInfo = "§9§o" + "Minecraft";
        } else if (id >= 512 && id <= 540) {
            return modInfo = "§9§o" + "MITE";
        } else {
            return modInfo = "§9§o" + ((IEntity) entity).getNamespace();
        }
    }

    public static String updateModInfo(RaycastCollision rc) {
        if (rc != null) {
            if (rc.isEntity()) {
                Entity entity = rc.getEntityHit();
                return updateModInfoByEntity(entity);
            }
            if (rc.isBlock()) {
                Block block = rc.getBlockHit();
                return updateModInfoByBlock(block);
            }
        }
        return "§9§o" + "Minecraft";
    }

    private static void hotKeyPress(RaycastCollision rc) {
        if (rc != null && rc.isBlock()) {
            EmiStack itemStack = EmiStack.of(rc.getBlockHit().createStackedBlock(mc.theWorld.getBlockMetadata(HwiteInfo.blockPosX, HwiteInfo.blockPosY, HwiteInfo.blockPosZ)));
            RecipeHotkey.setHotKeyPressCallBack(minecraft -> {
                EmiApi.displayRecipes(itemStack);
            });
            UsageHotkey.setHotKeyPressCallBack(minecraft -> {
                EmiApi.displayUses(itemStack);
            });
        }
    }
}
