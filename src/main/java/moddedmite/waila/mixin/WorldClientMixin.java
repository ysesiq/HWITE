package moddedmite.waila.mixin;

import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import mcp.mobius.waila.overlay.OverlayConfig;
import mcp.mobius.waila.overlay.WailaTickHandler;
import net.minecraft.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static moddedmite.waila.config.WailaConfig.*;


@Mixin(WorldClient.class)
public class WorldClientMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        DataAccessorCommon.instance = new DataAccessorCommon();
        WailaTickHandler.instance().tickClient();

    }
}
