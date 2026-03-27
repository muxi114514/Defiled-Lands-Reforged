package lykrast.defiledlands.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class GroundedEffect extends MobEffect {

    public GroundedEffect() {
        super(MobEffectCategory.HARMFUL, 0x5f5e57);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {

        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {

            if (!entity.onGround()) {
                Vec3 motion = entity.getDeltaMovement();

                if (motion.y > 0) {
                    entity.setDeltaMovement(motion.x, motion.y / (amplifier + 2), motion.z);
                }

                entity.setDeltaMovement(motion.x, motion.y - 0.05D * (amplifier + 1), motion.z);
            }
        }
    }
}
