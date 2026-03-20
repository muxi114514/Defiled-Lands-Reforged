package lykrast.defiledlands.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * Scarlite Ring - 缓慢恢复生命值
 * 每 10 秒（200 tick）恢复 0.5 颗心（1.0 生命值）
 */
public class ItemScarliteRing extends Item implements ICurioItem {
    private static final int HEAL_INTERVAL = 200; // 10 秒
    private static final float HEAL_AMOUNT = 1.0F; // 0.5 颗心

    public ItemScarliteRing(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide() && entity.tickCount % HEAL_INTERVAL == 0) {
            // 只在生命值未满时恢复
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(HEAL_AMOUNT);
            }
        }
    }
}
