package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.render.HUDRenderer;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.xylose.mitemod.hwite.config.HwiteConfigs.DebugRenderHud;
import static cn.xylose.mitemod.hwite.config.HwiteConfigs.RenderHwiteHud;


@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderHWITEHud(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        if (DebugRenderHud.getBooleanValue()) {
            if (mc.gameSettings.gui_mode == 0 && !mc.gameSettings.keyBindPlayerList.pressed && !mc.gameSettings.showDebugInfo && RenderHwiteHud.getBooleanValue() && !(mc.currentScreen instanceof GuiContainer)) {
                HUDRenderer.RenderHWITEHud(this, this.mc);
            }
        } else {
            if (mc.gameSettings.gui_mode == 0 && !mc.gameSettings.keyBindPlayerList.pressed && RenderHwiteHud.getBooleanValue() && !(mc.currentScreen instanceof GuiContainer)) {
                HUDRenderer.RenderHWITEHud(this, this.mc);
            }
        }
    }
}
