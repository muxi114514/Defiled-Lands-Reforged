package lykrast.defiledlands.common.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedingEffect extends MobEffect {

    private static final int TICK_RATE = 40;

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xff0000);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {

        return duration % TICK_RATE == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {

            entity.hurt(entity.damageSources().magic(), (float)(amplifier + 1));
        }
    }
}
