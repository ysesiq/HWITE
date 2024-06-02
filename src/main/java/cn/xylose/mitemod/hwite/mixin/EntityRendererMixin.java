package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.HwiteConfigs;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xylose.mitemod.hwite.client.HwiteModClient.*;
import static net.minecraft.EntityRenderer.setDebugInfoForSelectedObject;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;

    @Inject(method = "setDebugInfoForSelectedObject", at = @At("HEAD"))
    private static void setHwiteInfoForSelectedObject(RaycastCollision rc, EntityPlayer player, CallbackInfo ci) {
        if (rc != null) {
            if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                int id = block.blockID;
                if (id >= 164 && id < 170 || id >= 198 || id == 95) {
                    modInfo = EnumChatFormatting.ITALIC + "§1" + "MITE";
                } else if (id <= 163 || id >= 170 && id <= 174) {
                    modInfo = EnumChatFormatting.ITALIC + "§1" + "Minecraft";
                } else {
//                modInfo = EnumChatFormatting.ITALIC + "§1" + ModIdentification.getModName();
//                modInfo = ModMetadata.getName();
                    modInfo = EnumChatFormatting.ITALIC + "§1" + "Other Mod";
                }
            } else if (rc.isEntity()) {
                Entity entity = rc.getEntityHit();
                int id = entity.entityId;
                if (entity instanceof EntityLivingBase entityLivingBase) {
                    if (id <= 100 || id == 120 || id == 200) {
                        modInfo = EnumChatFormatting.ITALIC + "§1" + "Minecraft";
                    } else if (id >= 512 || id <= 540) {
                        modInfo = EnumChatFormatting.ITALIC + "§1" + "MITE";
                    } else {
                        modInfo = EnumChatFormatting.ITALIC + "§1" + "Other Mod";
                    }
                }
            }

            entityInfo = null;
            if (rc.isEntity()) {
                Entity entity = rc.getEntityHit();
                if (entity instanceof EntityLivingBase entityLivingBase) {
                    entityInfo = entityLivingBase;
                    float total_melee_damage;
                    if (entityLivingBase.isEntityPlayer()) {
                        total_melee_damage = entityLivingBase.getAsPlayer().calcRawMeleeDamageVs((Entity)null, false, false);
                    } else if (entityLivingBase.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
                        total_melee_damage = (float) entityLivingBase.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
                    } else {
                        total_melee_damage = 0.0F;
                    }
                    info = entityLivingBase.getEntityName();
                    if (HwiteConfigs.shiftMoreInfo.get()) {
                        if (GuiScreen.isShiftKeyDown()) {
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
                    } else {
                        if (total_melee_damage == 0.0F) {
                            info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth();
                            info_line_2 = "  ";
                            break_info = "  ";
                        } else {
                            info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + " 伤害:" + total_melee_damage;
                            info_line_2 = "  ";
                            break_info = "  ";
                        }
                    }
                } else {
                    info = entity.getTranslatedEntityName();
                    info_line_1 = " ";
                    info_line_2 = " ";
                    break_info = " ";
                }

            } else if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                blockInfo = block;
                float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int min_harvest_level = block.getMinHarvestLevel(metadata);
                info_line_1 = "";
                info_line_2 = "";
                break_info = "";
                info = block.getLocalizedName() +" (" + block.blockID + ":" + metadata + ")";
                if (HwiteConfigs.shiftMoreInfo.get()) {
                    if (GuiScreen.isShiftKeyDown()) {
                        if (min_harvest_level == 0) {
                            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness;
                                break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                            } else {
                                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                                break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
                            }
                        } else {
                            if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                                break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                            } else {
                                info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                                info_line_2 = EnumChatFormatting.DARK_GRAY + "挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                                break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
                            }
                        }
                    }
                } else {
                    if (min_harvest_level == 0) {
                        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                            info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness;
                            break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                        } else {
                            info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                            break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
                        }
                    } else {
                        if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                            info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                            break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                        } else {
                            info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                            info_line_2 = EnumChatFormatting.DARK_GRAY + "挖掘速度:" + (short) player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                            break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
                        }
                    }
                }
//                if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, false) > 0) {
//                    break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
//                } else if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
//                    break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
//                } else {
//                    break_info = "";
//                }
            } else {
                info_line_1 = "";
                info_line_2 = "";
//                break_info = "";
            }
        } else {
            info = "";
            info_line_1 = "";
            info_line_2 = "";
            break_info = "";
        }
    }

    @Inject(method = "getMouseOver", at = @At("TAIL"))
    public void getMouseOverHwite(float partial_tick, CallbackInfo ci) {
        EntityPlayer player = (EntityPlayer)this.mc.renderViewEntity;
        if (!Minecraft.inDevMode() || Minecraft.inDevMode()) {
            setDebugInfoForSelectedObject(player.getSelectedObject(partial_tick, HwiteConfigs.Liquids.get(), true, (EnumEntityReachContext) null), player);
        }
    }

}
