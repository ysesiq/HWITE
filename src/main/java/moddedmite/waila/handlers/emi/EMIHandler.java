package moddedmite.waila.handlers.emi;

import net.minecraft.Minecraft;

public class EMIHandler {
    static Minecraft mc = Minecraft.getMinecraft();

    public static dev.emi.emi.api.stack.EmiStack updateEmiStack() {
        if (mc.objectMouseOver != null && mc.objectMouseOver.isBlock()) {
            return dev.emi.emi.api.stack.EmiStack.of(mc.objectMouseOver.getBlockHit().createStackedBlock(mc.objectMouseOver.block_hit_metadata));
        }
        return null;
    }
}
