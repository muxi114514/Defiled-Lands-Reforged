package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Curios 饰品检查工具类
 * 使用反射避免在 common 模块直接依赖 Forge 的 LazyOptional 类型
 */
public class CuriosHelper {

    private CuriosHelper() {}

    /**
     * 检查实体是否佩戴了指定物品（通过反射调用 Curios API）
     */
    private static boolean hasEquipped(LivingEntity entity, Item item) {
        if (entity == null) return false;
        try {
            // CuriosApi.getCuriosInventory(entity) -> LazyOptional<ICuriosItemHandler>
            Class<?> curiosApiClass = Class.forName("top.theillusivec4.curios.api.CuriosApi");
            Object lazyOptional = curiosApiClass
                    .getMethod("getCuriosInventory", LivingEntity.class)
                    .invoke(null, entity);

            // LazyOptional.resolve() -> Optional<ICuriosItemHandler>
            Object optional = lazyOptional.getClass()
                    .getMethod("resolve")
                    .invoke(lazyOptional);

            // Optional.isPresent()
            boolean present = (boolean) optional.getClass()
                    .getMethod("isPresent")
                    .invoke(optional);
            if (!present) return false;

            // Optional.get() -> ICuriosItemHandler
            Object handler = optional.getClass()
                    .getMethod("get")
                    .invoke(optional);

            // ICuriosItemHandler.isEquipped(Item)
            return (boolean) handler.getClass()
                    .getMethod("isEquipped", Item.class)
                    .invoke(handler, item);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查实体是否佩戴了 Phytoprostasia Amulet（邪恶植物护符）
     */
    public static boolean hasPhytoprostasiaAmulet(LivingEntity entity) {
        return hasEquipped(entity, ModItems.PHYTOPROSTASIA_AMULET.get());
    }

    /**
     * 检查实体是否佩戴了 Scarlite Ring（绯红之戒）
     */
    public static boolean hasScarliteRing(LivingEntity entity) {
        return hasEquipped(entity, ModItems.SCARLITE_RING.get());
    }
}
