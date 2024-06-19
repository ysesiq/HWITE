package cn.xylose.mitemod.hwite;

import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import cn.xylose.mitemod.hwite.render.RenderItemHwite;
import net.minecraft.*;
import net.xiaoyu233.fml.api.block.IBlock;

import java.util.*;

import static cn.xylose.mitemod.hwite.render.HUDBackGroundRender.drawTooltipBackGround;
import static cn.xylose.mitemod.hwite.HwiteConfigs.*;
import static cn.xylose.mitemod.hwite.client.HwiteModClient.*;

public class HwiteInfo extends Gui {

    private static RenderItemHwite itemRenderBlocks = new RenderItemHwite();

    public static void setHwiteInfoForSelectedObject(RaycastCollision rc, EntityPlayer player) {
        if (rc != null) {
            if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                int id = block.blockID;

                blockPosX = rc.block_hit_x;
                blockPosY = rc.block_hit_y;
                blockPosZ = rc.block_hit_z;

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
                    modInfo = EnumChatFormatting.BLUE + "§o" + ((IBlock)block).getNamespace();
                }
            } else if (rc.isEntity()) {
                Entity entity = rc.getEntityHit();
                if (entity instanceof EntityLivingBase entityLivingBase) {
                    int id = EntityList.getEntityID(entityLivingBase);
                    if (id <= 100 || id == 120 || id == 200) {
                        modInfo = EnumChatFormatting.BLUE + "§o" + "Minecraft";
                    } else if (id >= 512 && id <= 540) {
                        modInfo = EnumChatFormatting.BLUE + "§o" + "MITE";
                    } else {
//                        modInfo = FishModLoader.getModContainer("modid").get().getMetadata().getName();
                        modInfo = EnumChatFormatting.BLUE + "§o" + "Other Mod";
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
                        total_melee_damage = entityLivingBase.getAsPlayer().calcRawMeleeDamageVs((Entity) null, false, false);
                    } else if (entityLivingBase.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
                        total_melee_damage = (float) entityLivingBase.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
                    } else {
                        total_melee_damage = 0.0F;
                    }
                    info = entityLivingBase.getEntityName();

                    if (total_melee_damage == 0.0F) {
                        info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth();
                        info_line_2 = " ";
                        break_info = "  ";
                    } else {
                        info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int) entityLivingBase.getHealth() + "/" + (int) entityLivingBase.getMaxHealth() + " 伤害:" + total_melee_damage;
                        info_line_2 = " ";
                        break_info = "  ";
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
                if (IDSUB.get()) {
                    info = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
                } else {
                    info = block.getLocalizedName();
                }
                if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                    break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                } else {
                    break_info = EnumChatFormatting.DARK_GREEN + "√" + EnumChatFormatting.WHITE;
                }

                if (shiftMoreInfo.get()) {
                    if (GuiScreen.isShiftKeyDown()) {
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
                } else {
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


    public static void RenderHWITEHud(Gui gui, Minecraft mc, double zLevel) {
        FontRenderer fontRenderer = mc.fontRenderer;
        ArrayList list = new ArrayList();
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = scaledResolution.getScaledWidth();
        int height = scaledResolution.getScaledHeight();

        int hud_x;
        int hud_y;
        int block_info_x;
        int block_info_y_small;
        int block_info_y_big;

        if (HUDPos.get()) {
            if (!shiftMoreInfo.get()) {
                hud_x = HUDX.get();
                block_info_x = HUDX.get() - 7;
            } else {
                hud_x = HUDX.get() + 25;
                block_info_x = HUDX.get() + 18;
            }
            hud_y = HUDY.get();
            if (!shiftMoreInfo.get()) {
                block_info_y_small = HUDY.get() - 6;
            } else {
                block_info_y_small = HUDY.get() - 10;
            }
            block_info_y_big = HUDY.get() - 2;
        } else {
            if (!shiftMoreInfo.get()) {
                hud_x = width / 2 - 50;
                block_info_x = width / 2 - 57;
            } else {
                hud_x = width / 2 - 25;
                block_info_x = width / 2 - 32;
            }
            hud_y = height - (height - 18);
            if (!shiftMoreInfo.get()) {
                block_info_y_small = height - (height - 12);
            } else {
                block_info_y_small = height - (height - 8);
            }
            block_info_y_big = height - (height - 12);
        }
        int entity_info_x = EntityInfoX.get();
        int entity_info_y = EntityInfoY.get();
        int entity_info_size = EntityInfoSize.get();


        if (mc.gameSettings.gui_mode == 0 && !mc.gameSettings.keyBindPlayerList.pressed && HwiteHud.get()) {

            int breakProgress = (int) (((IBreakingProgress) Minecraft.getMinecraft().playerController).getCurrentBreakingProgress() * 100);
            //draw model
            if (!Objects.equals(info, "") && entityInfo != null && EntityRender.get()) {
                //x, y, size, ?, ?
                GuiInventory.func_110423_a(entity_info_x, entity_info_y, entity_info_size, 0, 0, entityInfo);
            }
            //draw text and tooltip background
            if (!Objects.equals(info, "") && entityInfo != null) {
                list.add(info);
                if (breakProgress > 0 && BreakProgress.get()) {
                    list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
                }
                if (!Objects.equals(info_line_1, "")) {
                    list.add(info_line_1);
                }
                list.add(modInfo);
                drawTooltipBackGround(list, hud_x, hud_y, false, mc, zLevel);
            } else if (Objects.equals(info_line_2, "") && !Objects.equals(info, "")) {
                if (!Objects.equals(break_info, "") && CanBreak.get()) {
                    list.add(info + "  " + break_info);
                    if (breakProgress > 0 && BreakProgress.get()) {
                        list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
                    }
                    //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(width / 2 + 10, height - (height - 30), 0, 0, 16, 16);
                } else {
                    list.add(info);
                    if (breakProgress > 0 && BreakProgress.get()) {
                        list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
                    }
                }
                if (!Objects.equals(info_line_1, "")) {
                    list.add(info_line_1);
                }
                list.add(modInfo);
                drawTooltipBackGround(list, hud_x, hud_y, false, mc, zLevel);
                if (BlockRender.get() && blockInfo != null) {
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(blockPosX, blockPosY, blockPosZ)), block_info_x, block_info_y_small);
                }
            } else if (Objects.equals(info_line_1, " ") && Objects.equals(info_line_2, " ") && !Objects.equals(info, "")) {
                list.add(info);
                if (breakProgress > 0) {
                    list.add(String.format("%d", breakProgress) + "%");
                }
                list.add(modInfo);
                drawTooltipBackGround(list, hud_x, hud_y, false, mc, zLevel);
            } else if (!Objects.equals(info, "")) {
                if (!Objects.equals(break_info, "") && CanBreak.get()) {
                    list.add(info + "  " + break_info);
                    if (breakProgress > 0 && BreakProgress.get()) {
                        list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
                    }
                    //WIP
//                    mc.getTextureManager().bindTexture(hwiteIconTexPath);
//                    this.zLevel = -90.0F;
//                    this.drawTexturedModalRect(width / 2 + 10, height - (height - 30), 0, 0, 16, 16);
//                } else if (isViewMode && ViewMode.getBooleanValue()) {
//                    list.add(info);
//                    list.add(modInfo);
                } else {
                    list.add(info);
                    if (breakProgress > 0 && BreakProgress.get()) {
                        list.add(String.format(EnumChatFormatting.DARK_GRAY + "进度: " + "%d", breakProgress) + "%");
                    }
                }
                if (!Objects.equals(info_line_1, "") && !Objects.equals(info_line_2, "")) {
                    list.add(info_line_1);
                    list.add(info_line_2);
                }
                list.add(modInfo);
                drawTooltipBackGround(list, hud_x, hud_y, false, mc, zLevel);
                if (BlockRender.get() && blockInfo != null) {
                    itemRenderBlocks.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), blockInfo.createStackedBlock(mc.theWorld.getBlockMetadata(blockPosX, blockPosY, blockPosZ)), block_info_x, block_info_y_big);
                }
            }
        }
    }

}
