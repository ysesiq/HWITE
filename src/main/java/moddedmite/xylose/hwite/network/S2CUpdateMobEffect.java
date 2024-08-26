//package moddedmite.xylose.hwite.network;
//
//import net.minecraft.NetClientHandler;
//import net.minecraft.NetHandler;
//import net.minecraft.NetServerHandler;
//import net.minecraft.Packet;
//
//import java.io.DataInput;
//import java.io.DataOutput;
//import java.io.IOException;
//
//public class S2CUpdateMobEffect extends Packet {
//    private int name;
//    private int amplifier;
//    private int duration;
//
//    public S2CUpdateMobEffect() {
//    }
//
//    public S2CUpdateMobEffect(int name, int amplifier, int duration) {
//        this.name = name;
//        this.amplifier = amplifier;
//        this.duration = duration;
//    }
//
//    @Override
//    public void readPacketData(DataInput dataInput) throws IOException {
//        this.name = dataInput.readInt();
//        this.amplifier = dataInput.readInt();
//        this.duration = dataInput.readInt();
//    }
//
//    @Override
//    public void writePacketData(DataOutput dataOutput) throws IOException {
//        dataOutput.writeInt(this.name);
//        dataOutput.writeInt(this.amplifier);
//        dataOutput.writeInt(this.duration);
//    }
//
//    @Override
//    public void processPacket(NetHandler netHandler) {
//        if (netHandler instanceof NetServerHandler) {
//            throw new IllegalCallerException();
//        }
//        if (netHandler instanceof NetClientHandler netClientHandler) {
//            BreadSkinClientPlayer clientPlayer = netClientHandler.mc.thePlayer;
//            clientPlayer.breadSkin$SetPhytonutrients(this.name);
//            clientPlayer.breadSkin$SetProtein(this.amplifier);
//            clientPlayer.breadSkin$SetEssentialFats(this.duration);
//        }
//    }
//
//    @Override
//    public int getPacketSize() {
//        return 12;
//    }
//
//}
