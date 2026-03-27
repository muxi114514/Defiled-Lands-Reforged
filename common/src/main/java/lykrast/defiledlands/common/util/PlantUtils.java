package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class PlantUtils {
    private PlantUtils() {}

    public static boolean vulnerableToBlastem(Entity entity) {

        if (entity instanceof ItemEntity) return false;
        if (!entity.isAlive()) return false;

        if (entity instanceof IEntityDefiled defiled) {
            if (!defiled.affectedByBlastem()) return false;
        }

        if (entity instanceof Player player) {
            if (CuriosHelper.hasPhytoprostasiaAmulet(player)) return false;
        }

        return true;
    }

    public static boolean vulnerableToVilespine(Entity entity) {

        if (entity instanceof ItemEntity) return false;
        if (!entity.isAlive()) return false;

        if (entity instanceof IEntityDefiled defiled) {
            if (!defiled.affectedByVilespine()) return false;
        }

        if (entity instanceof Player player) {
            if (CuriosHelper.hasPhytoprostasiaAmulet(player)) return false;
        }

        return true;
    }
}
