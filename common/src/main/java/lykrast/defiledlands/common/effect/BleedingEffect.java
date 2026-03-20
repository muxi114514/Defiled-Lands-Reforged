package lykrast.defiledlands.common.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Bleeding（出血）效果
 * 持续造成伤害，可堆叠
 * 每 2 秒（40 tick）造成 (等级+1) 点伤害
 */
public class BleedingEffect extends MobEffect {
    
    private static final int TICK_RATE = 40; // 2 秒

    public BleedingEffect() {
        super(MobEffectCategory.HARMFUL, 0xff0000); // 红色
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 每 40 tick（2秒）触发一次
        return duration % TICK_RATE == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            // 造成魔法伤害，伤害值 = 等级 + 1
            entity.hurt(entity.damageSources().magic(), (float)(amplifier + 1));
        }
    }
}
