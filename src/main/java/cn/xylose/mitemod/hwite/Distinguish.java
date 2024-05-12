package cn.xylose.mitemod.hwite;

import net.minecraft.Block;

import static cn.xylose.mitemod.hwite.client.HwiteModClient.*;

public class Distinguish {

    public String BlockDistinguish(Block block) {
        int id = block.blockID;
        if (id >= 164 && id < 170 || id >= 198 || id == 95) {
            modInfo = "MITE";
        } else if (id <= 163 || id >= 170 && id <= 174) {
            modInfo = "Minecraft";
        } else {
//            modInfo = ModIdentification.getModName();
        }
        return "";
    }

//    public String EntityDistinguish(Block block) {
//        int id = EntityList.getEntityID();
//        if (id >= 164 && id < 170 || id >= 198 || id == 95) {
//            modInfo = "MITE";
//        } else if (id <= 163 || id >= 170 && id <= 174) {
//            modInfo = "Minecraft";
//        } else {
//            modInfo = ModIdentification.getModName();
//        }
//        return "";
//    }
}
