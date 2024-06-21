package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.client.HwiteModClient;
import cn.xylose.mitemod.hwite.render.HUDRenderer;
import net.minecraft.EntityPlayer;
import net.minecraft.Gui;
import net.minecraft.GuiIngame;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if (mc.gameSettings.gui_mode == 0 && !mc.gameSettings.keyBindPlayerList.pressed && RenderHwiteHud.getBooleanValue()) {
            HUDRenderer.RenderHWITEHud(this, this.mc, 300);
        }
    }
}
