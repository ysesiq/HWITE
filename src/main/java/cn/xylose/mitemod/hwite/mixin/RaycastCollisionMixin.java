package cn.xylose.mitemod.hwite.mixin;

import cn.xylose.mitemod.hwite.api.IRaycastCollision;
import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import org.spongepowered.asm.mixin.*;

@Mixin(RaycastCollision.class)
public abstract class RaycastCollisionMixin implements IRaycastCollision {

    @Mutable @Final @Shadow private final Object object_hit;
    @Mutable @Final @Shadow public final World world;

    @Unique public int tileentity_hit_x;
    @Unique public int tileentity_hit_y;
    @Unique public int tileentity_hit_z;
    @Unique public TileEntity tile_entity;
    @Unique public long timeLastUpdate = System.currentTimeMillis();
    @Unique public MovingObjectPosition mop;
    @Unique public NBTTagCompound remoteNbt = null;

    @Shadow public boolean isBlock() {
        return this.object_hit instanceof Block;
    }

    @Shadow public abstract boolean isLiquidBlock();

    public RaycastCollisionMixin(Raycast raycast, int x, int y, int z, EnumFace face_hit, Vec3 position_hit) {
        this.world = raycast.getWorld();
        Block block = this.world.getBlock(x, y, z);
        object_hit = block;
    }

    @Override
    public Block setBlockHit() {
        if (this.isBlock()) {
            return (Block)this.object_hit;
        }
        return null;
    }

    @Override
    public int setBlockHitID() {
        if (this.isBlock()) {
            return this.setBlockHit().blockID;
        }
        return 0;
    }

    @Override
    public boolean isLiquid(Material material) {
        if (material.isLiquid()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isTileEntity() {
        return this.object_hit instanceof TileEntity;
    }

    @Override
    public boolean isTileEntityAt(int x, int y, int z) {
        if (!this.isTileEntity()) {
            return false;
        }
        return x == this.tileentity_hit_x && y == this.tileentity_hit_y && z == this.tileentity_hit_z;
    }

    @Override
    public TileEntity getTileEntityHit() {
        if (this.isTileEntity()) {
            return (TileEntity) this.object_hit;
        }
        return null;
    }

    @Override
    public NBTTagCompound getNBTData() {
        if (this.tile_entity == null) return null;

        if (isTagCorrect(this.remoteNbt)) {
            return this.remoteNbt;
        }
        NBTTagCompound tag = new NBTTagCompound();
        this.tile_entity.writeToNBT(tag);
        return tag;
    }

    @Override
    public int getNBTInteger(NBTTagCompound tag, String key) {
        NBTBase subtag = tag.getTag(key);
        if (subtag instanceof NBTTagInt)
            return tag.getInteger(key);
        if (subtag instanceof NBTTagShort)
            return tag.getShort(key);
        if (subtag instanceof NBTTagByte) {
            return tag.getByte(key);
        }
        return 0;
    }

    @Override
    public boolean isTagCorrect(NBTTagCompound tag) {
        if (tag == null) {
            this.timeLastUpdate = System.currentTimeMillis() - 250L;
            return false;
        }

        int x = tag.getInteger("x");
        int y = tag.getInteger("y");
        int z = tag.getInteger("z");

        if (x == this.mop.blockX && y == this.mop.blockY && z == this.mop.blockZ) {
            return true;
        }
        this.timeLastUpdate = System.currentTimeMillis() - 250L;
        return false;
    }


}
