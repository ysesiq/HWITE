//package mcp.mobius.waila.overlay;
//
//import java.util.List;
//
//import net.minecraft.Block;
//import net.minecraft.Minecraft;
//import net.minecraft.EntityLivingBase;
//import net.minecraft.EntityPlayer;
//import net.minecraft.World;
//import org.lwjgl.opengl.GL11;
//
//import mcp.mobius.waila.api.IWailaBlockDecorator;
//import mcp.mobius.waila.api.impl.ConfigHandler;
//import mcp.mobius.waila.api.impl.DataAccessorCommon;
//import mcp.mobius.waila.api.impl.ModuleRegistrar;
//import mcp.mobius.waila.utils.WailaExceptionHandler;
//import org.spongepowered.asm.mixin.MixinEnvironment;
//
//public class DecoratorRenderer {
//
//    public void onRenderWorldLast(RenderWorldLastEvent event) {
//        if (RayTracing.instance().getTarget() == null || RayTracing.instance().getTargetStack() == null) return;
//
//        double partialTicks = event.partialTicks;
//
//        DataAccessorCommon accessor = DataAccessorCommon.instance;
//        World world = Minecraft.getMinecraft().theWorld;
//        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
//        EntityLivingBase viewEntity = Minecraft.getMinecraft().renderViewEntity;
//
//        if (world == null || player == null || viewEntity == null) return;
//
//        accessor.set(world, player, RayTracing.instance().getTarget(), viewEntity, partialTicks);
//
//        Block block = accessor.getBlock();
//
//        if (!ModuleRegistrar.instance().hasBlockDecorator(block)) return;
//
//        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//        GL11.glDepthMask(false);
//
//        if (ModuleRegistrar.instance().hasBlockDecorator(block)) {
//            for (List<IWailaBlockDecorator> decoratorsList : ModuleRegistrar.instance().getBlockDecorators(block)
//                    .values()) {
//                for (IWailaBlockDecorator decorator : decoratorsList) try {
//                    GL11.glPushMatrix();
//                    decorator.decorateBlock(RayTracing.instance().getTargetStack(), accessor, ConfigHandler.instance());
//                    GL11.glPopMatrix();
//                } catch (Throwable e) {
//                    GL11.glPopMatrix();
//                    WailaExceptionHandler.handleErr(e, decorator.getClass().toString(), null);
//                }
//            }
//        }
//
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glPopAttrib();
//
//    }
//}
