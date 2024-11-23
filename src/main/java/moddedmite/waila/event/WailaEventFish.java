package moddedmite.waila.event;

import com.google.common.eventbus.Subscribe;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.network.Packet0x00ServerPing;
import moddedmite.waila.api.PacketDispatcher;
import net.xiaoyu233.fml.reload.event.PlayerLoggedInEvent;

public class WailaEventFish {
    @Subscribe
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        Waila.log.info(String.format("Player %s connected. Sending ping", event.getPlayer()));
        PacketDispatcher.sendPacketToPlayer(Packet0x00ServerPing.create(), event.getPlayer());
    }
}
