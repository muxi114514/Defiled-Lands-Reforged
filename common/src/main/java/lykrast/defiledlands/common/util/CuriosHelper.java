package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CuriosHelper {

    private CuriosHelper() {}

    private static boolean hasEquipped(LivingEntity entity, Item item) {
        if (entity == null) return false;
        try {

            Class<?> curiosApiClass = Class.forName("top.theillusivec4.curios.api.CuriosApi");
            Object lazyOptional = curiosApiClass
                    .getMethod("getCuriosInventory", LivingEntity.class)
                    .invoke(null, entity);

            Object optional = lazyOptional.getClass()
                    .getMethod("resolve")
                    .invoke(lazyOptional);

            boolean present = (boolean) optional.getClass()
                    .getMethod("isPresent")
                    .invoke(optional);
            if (!present) return false;

            Object handler = optional.getClass()
                    .getMethod("get")
                    .invoke(optional);

            return (boolean) handler.getClass()
                    .getMethod("isEquipped", Item.class)
                    .invoke(handler, item);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasPhytoprostasiaAmulet(LivingEntity entity) {
        return hasEquipped(entity, ModItems.PHYTOPROSTASIA_AMULET.get());
    }

    public static boolean hasScarliteRing(LivingEntity entity) {
        return hasEquipped(entity, ModItems.SCARLITE_RING.get());
    }
}
