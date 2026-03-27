package lykrast.defiledlands.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ItemScarliteRing extends Item implements ICurioItem {
    private static final int HEAL_INTERVAL = 200;
    private static final float HEAL_AMOUNT = 1.0F;

    public ItemScarliteRing(Properties properties) {
        super(properties);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (entity != null && !entity.level().isClientSide() && entity.tickCount % HEAL_INTERVAL == 0) {

            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(HEAL_AMOUNT);
            }
        }
    }
}
