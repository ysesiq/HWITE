package moddedmite.waila.mixin;

import mcp.mobius.waila.api.impl.DataAccessorCommon;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "startGame", at = @At("TAIL"))
    private void onWorldUnload(CallbackInfo ci) {
    }
}
