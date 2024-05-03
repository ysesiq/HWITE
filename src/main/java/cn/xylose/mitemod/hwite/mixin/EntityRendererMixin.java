package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.HwiteMod;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "setDebugInfoForSelectedObject", at = @At("HEAD"))
    private static void setHwiteInfoForSelectedObject(RaycastCollision rc, EntityPlayer player, CallbackInfo ci) {
        if (rc != null) {
            HwiteMod.entityInfo = null;
            if (rc.isEntity()) {
                Entity entity = rc.getEntityHit();
                if (entity instanceof EntityLivingBase entityLivingBase) {
                    HwiteMod.entityInfo = entityLivingBase;
                    float total_melee_damage;
                    if (entityLivingBase.isEntityPlayer()) {
                        total_melee_damage = entityLivingBase.getAsPlayer().calcRawMeleeDamageVs((Entity)null, false, false);
                    } else if (entityLivingBase.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
                        total_melee_damage = (float)entityLivingBase.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
                    } else {
                        total_melee_damage = 0.0F;
                    }
                    HwiteMod.info = entityLivingBase.getEntityName() + " 血量:" + entityLivingBase.getMaxHealth() + "/" + entityLivingBase.getHealth() + " 伤害:" + total_melee_damage;
                } else {
                    HwiteMod.info = entity.getTranslatedEntityName();
                }

                HwiteMod.info_line_1 = "";
            } else if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int min_harvest_level = block.getMinHarvestLevel(metadata);
                HwiteMod.info = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
                if (min_harvest_level == 0) {
                    if(player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) >= 0) {
                            HwiteMod.info_line_1 = "硬度:" + block_hardness + " 有效挖掘速度:" + player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                        } else {
                            HwiteMod.info_line_1 = "硬度:" + block_hardness + " 无法破坏";
                        }
                    } else {
                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) >= 0) {
                            HwiteMod.info_line_1 = "硬度:" + block_hardness + " 最低挖掘等级=" + min_harvest_level + " 有效挖掘速度:" + player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                        } else {
                            HwiteMod.info_line_1 = "硬度:" + block_hardness + " 最低挖掘等级=" + min_harvest_level + " 无法破坏";
                        }
                    }
                } else {
                HwiteMod.info_line_1 = "";
            }
        } else {
            HwiteMod.info = "";
            HwiteMod.info_line_1 = "";
        }

    }
}
