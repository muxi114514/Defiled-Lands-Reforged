package lykrast.defiledlands.common.event;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import lykrast.defiledlands.common.item.ModTiers;
import lykrast.defiledlands.common.registry.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;

public class EffectEventHandler {

    private static final int BLEED_DURATION = 120;

    public static void register() {
        EntityEvent.LIVING_HURT.register((entity, source, amount) -> {

            if (entity.level().isClientSide)
                return EventResult.pass();

            if (!(source.getEntity() instanceof LivingEntity attacker))
                return EventResult.pass();

            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.getItem() instanceof TieredItem tiered && tiered.getTier() == ModTiers.UMBRIUM) {
                entity.addEffect(new MobEffectInstance(ModEffects.BLEEDING.get(), BLEED_DURATION, 0));
            }

            return EventResult.pass();
        });
    }
}
