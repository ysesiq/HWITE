package mcp.mobius.waila.addons.vanillamc;

import java.util.List;

import net.minecraft.*;
import net.minecraft.ServerPlayer;
import net.minecraft.Block;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.cbcore.LangUtil;

public class HUDHandlerVanilla implements IWailaDataProvider {

    static Block mobSpawner = Block.mobSpawner;
    static Block crops = Block.crops;
    static Block melonStem = Block.melonStem;
    static Block pumpkinStem = Block.pumpkinStem;
    static Block carrot = Block.carrot;
    static Block potato = Block.potato;
    static Block lever = Block.lever;
    static Block repeaterIdle = Block.redstoneRepeaterIdle;
    static Block repeaterActv = Block.redstoneRepeaterActive;
    static Block comparatorIdl = Block.redstoneComparatorIdle;
    static Block comparatorAct = Block.redstoneComparatorActive;
    static Block redstone = Block.redstoneWire;
    static Block jukebox = Block.jukebox;
    static Block cocoa = Block.cocoaPlant;
    static Block netherwart = Block.netherStalk;
    static Block silverfish = Block.silverfish;
//    static Block doubleplant = Block.double_plant;
    static Block leave = Block.leaves;
//    static Block leave2 = Block.leaves2;
    static Block log = Block.wood;
//    static Block log2 = Block.log2;
    static Block quartz = Block.blockNetherQuartz;
    static Block anvil = Block.anvil;
    static Block sapling = Block.sapling;

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();

        if (block == silverfish && config.getConfig("vanilla.silverfish")) {
            int metadata = accessor.getMetadata();
            return switch (metadata) {
                case 0 -> new ItemStack(Block.stone);
                case 1 -> new ItemStack(Block.cobblestone);
                case 2 -> new ItemStack(Block.brick);
                default -> null;
            };
        }

        if (block == redstone) {
            return new ItemStack(Item.redstone);
        }

//        if (block == doubleplant && (accessor.getMetadata() & 8) != 0) {
//            int x = accessor.getPosition().blockX;
//            int y = accessor.getPosition().blockY - 1;
//            int z = accessor.getPosition().blockZ;
//            int meta = accessor.getWorld().getBlockMetadata(x, y, z);
//
//            return new ItemStack(doubleplant, 0, meta);
//        }

        if (block instanceof BlockRedstoneOre) {
            return new ItemStack(Block.oreRedstone);
        }

        if (block == crops) {
            return new ItemStack(Item.wheat);
        }

        if ((block == leave) && (accessor.getMetadata() > 3)) {
            return new ItemStack(block, 1, accessor.getMetadata() - 4);
        }

        if (block == log) {
            return new ItemStack(block, 1, accessor.getMetadata() % 4);
        }

        if ((block == quartz) && (accessor.getMetadata() > 2)) {
            return new ItemStack(block, 1, 2);
        }

//        if (block == anvil) {
//            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
//        }
//
//        if (block == sapling) {
//            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
//        }
//
//        if (block instanceof BlockStoneSlab || block instanceof BlockWoodSlab) {
//            return new ItemStack(block, 1, block.damageDropped(accessor.getMetadata()));
//        }

        return null;

    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();

        /* Mob spawner handler */
        if (block == mobSpawner && accessor.getTileEntity() instanceof TileEntityMobSpawner
                && config.getConfig("vanilla.spawntype")) {
            String name = currenttip.get(0);
            String mobname = ((TileEntityMobSpawner) accessor.getTileEntity()).getSpawnerLogic().getEntityNameToSpawn();
            currenttip.set(0, String.format("%s (%s)", name, mobname));
        }

        if (block == redstone) {
            String name = currenttip.get(0).replaceFirst(String.format(" %s", accessor.getMetadata()), "");
            currenttip.set(0, name);
        }

        if (block == melonStem) {
            currenttip.set(0, SpecialChars.WHITE + "Melon stem");
        }

        if (block == pumpkinStem) {
            currenttip.set(0, SpecialChars.WHITE + "Pumpkin stem");
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        /* Crops */
        boolean iscrop = crops.getClass().isInstance(block); // Done to cover all inheriting mods
        if (config.getConfig("general.showcrop"))
            if (iscrop || block == melonStem || block == pumpkinStem || block == carrot || block == potato) {
                float growthValue = (accessor.getMetadata() / 7.0F) * 100.0F;
                if (growthValue < 100.0)
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                else currenttip.add(
                        String.format(
                                "%s : %s",
                                LangUtil.translateG("hud.msg.growth"),
                                LangUtil.translateG("hud.msg.mature")));
                return currenttip;
            }

        if (block == cocoa && config.getConfig("general.showcrop")) {

            float growthValue = ((accessor.getMetadata() >> 2) / 2.0F) * 100.0F;
            if (growthValue < 100.0)
                currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
            else currenttip.add(
                    String.format(
                            "%s : %s",
                            LangUtil.translateG("hud.msg.growth"),
                            LangUtil.translateG("hud.msg.mature")));
            return currenttip;
        }

        if (block == netherwart && config.getConfig("general.showcrop")) {
            float growthValue = (accessor.getMetadata() / 3.0F) * 100.0F;
            if (growthValue < 100.0)
                currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
            else currenttip.add(
                    String.format(
                            "%s : %s",
                            LangUtil.translateG("hud.msg.growth"),
                            LangUtil.translateG("hud.msg.mature")));
            return currenttip;
        }

        if (config.getConfig("vanilla.leverstate")) if (block == lever) {
            String redstoneOn = (accessor.getMetadata() & 8) == 0 ? LangUtil.translateG("hud.msg.off")
                    : LangUtil.translateG("hud.msg.on");
            currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.state"), redstoneOn));
            return currenttip;
        }

        if (config.getConfig("vanilla.repeater")) if ((block == repeaterIdle) || (block == repeaterActv)) {
            int tick = (accessor.getMetadata() >> 2) + 1;
            if (tick == 1) currenttip.add(String.format("%s : %s tick", LangUtil.translateG("hud.msg.delay"), tick));
            else currenttip.add(String.format("%s : %s ticks", LangUtil.translateG("hud.msg.delay"), tick));
            return currenttip;
        }

        if (config.getConfig("vanilla.comparator")) if ((block == comparatorIdl) || (block == comparatorAct)) {
            String mode = ((accessor.getMetadata() >> 2) & 1) == 0 ? LangUtil.translateG("hud.msg.comparator")
                    : LangUtil.translateG("hud.msg.substractor");
            currenttip.add("Mode : " + mode);
            return currenttip;
        }

        if (config.getConfig("vanilla.redstone")) if (block == redstone) {
            currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.power"), accessor.getMetadata()));
            return currenttip;
        }

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

    public static void register() {
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.spawntype");
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.leverstate");
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.repeater");
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.comparator");
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.redstone");
//        ModuleRegistrar.instance().addConfig("VanillaMC", "vanilla.silverfish");
//        ModuleRegistrar.instance().addConfigRemote("VanillaMC", "vanilla.jukebox");
//        ModuleRegistrar.instance().addConfigRemote("VanillaMC", "vanilla.show_invisible_players");

        IWailaDataProvider provider = new HUDHandlerVanilla();

        ModuleRegistrar.instance().registerStackProvider(provider, silverfish.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, redstone.getClass());
//        ModuleRegistrar.instance().registerStackProvider(provider, doubleplant.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, BlockRedstoneOre.class);
        ModuleRegistrar.instance().registerStackProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, leave.getClass());
//        ModuleRegistrar.instance().registerStackProvider(provider, leave2.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, log.getClass());
//        ModuleRegistrar.instance().registerStackProvider(provider, log2.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, quartz.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, anvil.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, sapling.getClass());
        ModuleRegistrar.instance().registerStackProvider(provider, BlockSlab.class);
        ModuleRegistrar.instance().registerStackProvider(provider, BlockWoodSlab.class);

        ModuleRegistrar.instance().registerHeadProvider(provider, mobSpawner.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, pumpkinStem.getClass());

        ModuleRegistrar.instance().registerBodyProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, pumpkinStem.getClass());

        ModuleRegistrar.instance().registerBodyProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerHeadProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, jukebox.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, cocoa.getClass());
        ModuleRegistrar.instance().registerBodyProvider(provider, netherwart.getClass());

        ModuleRegistrar.instance().registerNBTProvider(provider, mobSpawner.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, crops.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, melonStem.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, pumpkinStem.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, carrot.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, potato.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, lever.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterIdle.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, repeaterActv.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorIdl.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, comparatorAct.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, redstone.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, jukebox.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, cocoa.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, netherwart.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, silverfish.getClass());
    }

}
