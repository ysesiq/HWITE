package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.info.HwiteInfo;
import net.minecraft.*;
import net.xiaoyu233.fml.api.INamespaced;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.*;
import static net.minecraft.EntityRenderer.setDebugInfoForSelectedObject;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin implements INamespaced {

    @Shadow
    private Minecraft mc;

    @Inject(method = "setDebugInfoForSelectedObject", at = @At("HEAD"))
    private static void setHwiteInfoForSelectedObject(RaycastCollision rc, EntityPlayer player, CallbackInfo ci) {
        HwiteInfo.updateInfoForRC(rc, player);
    }

    @Inject(method = "getMouseOver", at = @At("TAIL"))
    public void getMouseOverHwite(float partial_tick, CallbackInfo ci) {
        EntityPlayer player = (EntityPlayer) this.mc.renderViewEntity;
        setDebugInfoForSelectedObject(player.getSelectedObject(partial_tick, Liquids.getBooleanValue(), NonCollidingEntity.getBooleanValue(), null), player);
    }

}
