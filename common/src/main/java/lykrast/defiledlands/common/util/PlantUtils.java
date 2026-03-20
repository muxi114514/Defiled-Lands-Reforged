package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class PlantUtils {
    private PlantUtils() {}

    /**
     * 判断给定实体是否会被成熟的爆炸植物（Blastem）触发爆炸。
     * 污秽之地原生生物（IEntityDefiled）默认不受影响；
     * 佩戴 Phytoprostasia Amulet 的玩家也不会触发。
     */
    public static boolean vulnerableToBlastem(Entity entity) {
        // 物品实体、经验球等非生命实体不触发
        if (entity instanceof ItemEntity) return false;
        if (!entity.isAlive()) return false;

        // 污秽之地原生生物默认不受影响（除非覆写 affectedByBlastem 返回 true）
        if (entity instanceof IEntityDefiled defiled) {
            if (!defiled.affectedByBlastem()) return false;
        }

        // 佩戴护身符的玩家不触发
        if (entity instanceof Player player) {
            if (CuriosHelper.hasPhytoprostasiaAmulet(player)) return false;
        }

        return true;
    }

    /**
     * 判断给定实体是否受恶棘（Vilespine）伤害影响。
     * 污秽之地原生生物（IEntityDefiled）默认不受影响；
     * 佩戴 Phytoprostasia Amulet 的玩家也不受伤害。
     */
    public static boolean vulnerableToVilespine(Entity entity) {
        // 物品实体等不受影响
        if (entity instanceof ItemEntity) return false;
        if (!entity.isAlive()) return false;

        // 污秽之地原生生物默认不受影响（除非覆写 affectedByVilespine 返回 true）
        if (entity instanceof IEntityDefiled defiled) {
            if (!defiled.affectedByVilespine()) return false;
        }

        // 佩戴护身符的玩家不受伤害
        if (entity instanceof Player player) {
            if (CuriosHelper.hasPhytoprostasiaAmulet(player)) return false;
        }

        return true;
    }
}
