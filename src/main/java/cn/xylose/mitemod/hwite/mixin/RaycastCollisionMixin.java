//package cn.xylose.mitemod.hwite.mixin;
//
//import net.minecraft.*;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(RaycastCollision.class)
//public class RaycastCollisionMixin {
//    @Shadow
//    public final Raycast raycast;
//    @Shadow
//    public final World world;
//    @Shadow
//    private final Object object_hit;
//    @Shadow
//    public int block_hit_x;
//    @Shadow
//    public int block_hit_y;
//    @Shadow
//    public int block_hit_z;
//    @Shadow
//    public float block_hit_offset_x;
//    @Shadow
//    public float block_hit_offset_y;
//    @Shadow
//    public float block_hit_offset_z;
//    @Shadow
//    public int block_hit_metadata;
//    @Shadow
//    public int neighbor_block_x;
//    @Shadow
//    public int neighbor_block_y;
//    @Shadow
//    public int neighbor_block_z;
//    @Shadow
//    public final EnumFace face_hit;
//    @Shadow
//    public final Vec3 position_hit;
//
//    public RaycastCollisionMixin(Raycast raycast, Entity entity_hit, AABBIntercept intercept) {
//        this.raycast = raycast.setHasProducedCollisions();
//        this.world = entity_hit.worldObj;
//        this.object_hit = entity_hit;
//        this.face_hit = intercept.face_hit;
//        this.position_hit = intercept.position_hit;
//    }
//
//    @Inject(method = "isEntity", at = @At("TAIL"), cancellable = true)
//    public void isEntity(CallbackInfoReturnable<Boolean> cir) {
//        cir.setReturnValue(this.object_hit instanceof EntityLivingBase);
//    }
//
//}
