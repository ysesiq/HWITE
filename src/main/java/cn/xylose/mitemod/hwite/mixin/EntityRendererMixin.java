package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.client.HwiteModClient;
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
                    if (total_melee_damage == 0.0F) {
                        info = entityLivingBase.getEntityName() + " 血量:" + (int)entityLivingBase.getHealth() + "/" + (int)entityLivingBase.getMaxHealth();
                        info_line_1 = "";
                    } else {
                        info = entityLivingBase.getEntityName();
                        info_line_1 = EnumChatFormatting.GRAY + "血量:" + (int)entityLivingBase.getHealth() + "/" + (int)entityLivingBase.getMaxHealth() + " 伤害:" + total_melee_damage;
                    }
                } else {
                    info = entity.getTranslatedEntityName();
                    info_line_1 = " ";
                    info_line_2 = " ";
                }

            } else if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                blockInfo = block;
                float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int min_harvest_level = block.getMinHarvestLevel(metadata);
                info_line_1 = "";
                info_line_2 = "";
                info = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
                if (min_harvest_level == 0) {
                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                        info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness;
                        info_line_2 = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                    } else {
                        info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘速度:" + (byte)player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                    }
                } else {
                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
                        info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                        info_line_2 = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
                    } else {
                        info_line_1 = EnumChatFormatting.GRAY + "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                        info_line_2 = EnumChatFormatting.DARK_GRAY + "挖掘速度:" + (byte)player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                    }
//                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) <= 0.0) {
//                        break_info = EnumChatFormatting.DARK_RED + "X" + EnumChatFormatting.WHITE;
//                    } else {
//                        break_info = EnumChatFormatting.GREEN + "√" + EnumChatFormatting.WHITE;
//                    }
                }
            } else {
                info_line_1 = "";
                info_line_2 = "";
            }
        } else {
            info = "";
            info_line_1 = "";
            info_line_2 = "";
        }
    }

    @Inject(method = "getMouseOver", at = @At("TAIL"))
    public void getMouseOver(float partial_tick, CallbackInfo ci) {
        EntityPlayer player = (EntityPlayer)this.mc.renderViewEntity;
        if (!Minecraft.inDevMode() || Minecraft.inDevMode()) {
            setDebugInfoForSelectedObject(player.getSelectedObject(partial_tick, false, true, (EnumEntityReachContext) null), player);
        }
    }

}
