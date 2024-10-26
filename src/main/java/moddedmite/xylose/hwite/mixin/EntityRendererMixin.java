package moddedmite.xylose.hwite.mixin;

import moddedmite.xylose.hwite.info.HwiteInfo;
import moddedmite.xylose.hwite.config.HwiteConfigs;
import net.minecraft.*;
import net.xiaoyu233.fml.api.INamespaced;
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
        if (rc != null)
            HwiteInfo.updateInfoForRC(rc, player);
    }

    @Inject(method = "getMouseOver", at = @At("TAIL"))
    public void getMouseOverHwite(float partial_tick, CallbackInfo ci) {
        EntityPlayer player = (EntityPlayer) this.mc.renderViewEntity;
        setDebugInfoForSelectedObject(player.getSelectedObject(partial_tick, HwiteConfigs.DisplayLiquids.getBooleanValue(), HwiteConfigs.DisplayNonCollidingEntity.getBooleanValue(), null), player);
    }

}
