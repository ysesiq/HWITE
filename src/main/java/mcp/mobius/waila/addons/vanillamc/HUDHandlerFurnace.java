package mcp.mobius.waila.addons.vanillamc;

import java.util.List;

import net.minecraft.ServerPlayer;
import net.minecraft.ItemStack;
import net.minecraft.NBTTagCompound;
import net.minecraft.NBTTagList;
import net.minecraft.TileEntity;
import net.minecraft.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;

public class HUDHandlerFurnace implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        int cookTime = accessor.getNBTData().getShort("CookTime");
        NBTTagList tag = accessor.getNBTData().getTagList("Items");

        String renderStr = "";
        {
            ItemStack stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.tagAt(0));
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    stack.getDisplayName(),
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }
        {
            ItemStack stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.tagAt(1));
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    stack.getDisplayName(),
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }

        renderStr += SpecialChars.getRenderString("waila.progress", String.valueOf(cookTime), String.valueOf(200));

        {
            ItemStack stack = ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.tagAt(2));
            renderStr += SpecialChars.getRenderString(
                    "waila.stack",
                    "1",
                    stack.getDisplayName(),
                    String.valueOf(stack.stackSize),
                    String.valueOf(stack.getItemDamage()));
        }

        currenttip.add(renderStr);

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(ServerPlayer player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        if (te != null) te.writeToNBT(tag);
        return tag;
    }

    public static void register() {}
}
