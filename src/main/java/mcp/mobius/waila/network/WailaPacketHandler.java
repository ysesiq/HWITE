package mcp.mobius.waila.network;

import mcp.mobius.waila.Waila;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import mcp.mobius.waila.WailaExceptionHandler;
import mcp.mobius.waila.api.impl.DataAccessorCommon;
import moddedmite.waila.api.PacketDispatcher;
import moddedmite.waila.config.WailaConfig;
import net.minecraft.NBTTagCompound;
import net.minecraft.NetServerHandler;
import net.minecraft.Packet250CustomPayload;
import net.minecraft.TileEntity;
import net.minecraft.TileEntityFurnace;
import net.minecraft.TileEntitySkull;
import net.minecraft.server.MinecraftServer;

public class WailaPacketHandler {
    public void handleCustomPacket(Packet250CustomPayload packet) {
        if (packet.channel.equals("Waila")) {
            try {
                byte header = getHeader(packet);
                if (header == 0) {
                    Waila.log.info("Received server authentication msg. Remote sync will be activated");
                    Waila.instance.serverPresent = true;
                } else if (header == 2) {
                    Packet0x02TENBTData castedPacket = new Packet0x02TENBTData(packet);
                    DataAccessorCommon.instance.remoteNbt = castedPacket.tag;
                }
            } catch (Exception e) {
            }
        }
    }

    public void handleCustomPacket(NetServerHandler handler, Packet250CustomPayload packet) {
        if (packet.channel.equals("Waila")) {
            try {
                byte header = getHeader(packet);
                if (header == 1) {
                    Packet0x01TERequest castedPacket = new Packet0x01TERequest(packet);
                    MinecraftServer server = MinecraftServer.getServer();
                    TileEntity entity = server.worldServers[castedPacket.worldID].getBlockTileEntity(castedPacket.posX, castedPacket.posY, castedPacket.posZ);
                    if (entity instanceof TileEntityFurnace) {
                        if (!WailaConfig.showSkull.getBooleanValue() && (entity instanceof TileEntitySkull)) {
                            return;
                        }
                        try {
                            NBTTagCompound tag = new NBTTagCompound();
                            entity.writeToNBT(tag);
                            PacketDispatcher.sendPacketToPlayer(Packet0x02TENBTData.create(tag), handler.playerEntity);
                        } catch (Throwable e) {
                            WailaExceptionHandler.handleErr(e, entity.getClass().toString(), null);
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public byte getHeader(Packet250CustomPayload packet) {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            return inputStream.readByte();
        } catch (IOException e) {
            return (byte) -1;
        }
    }
}
