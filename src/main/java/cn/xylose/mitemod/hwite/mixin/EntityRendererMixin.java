package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.HwiteMod;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.EntityRenderer.setDebugInfoForSelectedObject;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Shadow
    private Minecraft mc;

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
                        total_melee_damage = entityLivingBase.getAsPlayer().calcRawMeleeDamageVs((Entity) null, false, false);
                    } else if (entityLivingBase.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
                        total_melee_damage = (float) entityLivingBase.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
                    } else {
                        total_melee_damage = 0.0F;
                    }
                    if (total_melee_damage == 0.0F) {
                        HwiteMod.info = entityLivingBase.getEntityName() + " 血量:" + entityLivingBase.getHealth() + "/" + entityLivingBase.getMaxHealth();
                        HwiteMod.info_line_1 = "";
                    } else {
                        HwiteMod.info = entityLivingBase.getEntityName();
                        HwiteMod.info_line_1 = "血量:" + entityLivingBase.getHealth() + "/" + entityLivingBase.getMaxHealth() + " 伤害:" + total_melee_damage;
                    }
                } else {
                    HwiteMod.info = entity.getTranslatedEntityName();
                }

            } else if (rc.isBlock()) {
                Block block = Block.blocksList[player.worldObj.getBlockId(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z)];
                float block_hardness = player.worldObj.getBlockHardness(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int metadata = player.worldObj.getBlockMetadata(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z);
                int min_harvest_level = block.getMinHarvestLevel(metadata);
                HwiteMod.info_line_1 = "";
                HwiteMod.info_line_2 = "";
                HwiteMod.info = block.getLocalizedName() + " (" + block.blockID + ":" + metadata + ")";
                if (min_harvest_level == 0) {
                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) == 0.0) {
                        HwiteMod.info_line_1 = "硬度:" + block_hardness;
                        HwiteMod.info_line_2 = EnumChatFormatting.DARK_RED + "×" + EnumChatFormatting.WHITE;
                    } else {
                        HwiteMod.info_line_1 = "硬度:" + block_hardness + " 挖掘速度:" + player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                    }
                } else {
                    if (player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true) == 0.0) {
                        HwiteMod.info_line_1 = "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                        HwiteMod.info_line_2 = EnumChatFormatting.DARK_RED + "×" + EnumChatFormatting.WHITE;
                    } else {
                        HwiteMod.info_line_1 = "硬度:" + block_hardness + " 挖掘等级:" + min_harvest_level;
                        HwiteMod.info_line_2 = "挖掘速度:" + player.getCurrentPlayerStrVsBlock(rc.block_hit_x, rc.block_hit_y, rc.block_hit_z, true);
                    }
                }
            } else {
                HwiteMod.info_line_1 = "";
                HwiteMod.info_line_2 = "";
            }
        } else {
            HwiteMod.info = "";
            HwiteMod.info_line_1 = "";
            HwiteMod.info_line_2 = "";
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
