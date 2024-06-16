package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.api.IBreakingProgress;
import net.minecraft.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerControllerMP.class)
public abstract class PlayerControllerMPMixin implements IBreakingProgress {

    @Shadow private float curBlockDamageMP;

    @Override
    public float getCurrentBreakingProgress() {
        return this.curBlockDamageMP;
    }
}
