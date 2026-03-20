package lykrast.defiledlands.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * Grounded（沉重）效果
 * 使实体无法跳跃，并在空中时快速下坠
 */
public class GroundedEffect extends MobEffect {
    
    public GroundedEffect() {
        super(MobEffectCategory.HARMFUL, 0x5f5e57); // 深灰色
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 每 tick 都需要检查
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            // 如果实体在空中（不在地面上）
            if (!entity.onGround()) {
                Vec3 motion = entity.getDeltaMovement();
                
                // 如果向上移动，减少向上速度
                if (motion.y > 0) {
                    entity.setDeltaMovement(motion.x, motion.y / (amplifier + 2), motion.z);
                }
                
                // 增加下坠速度
                entity.setDeltaMovement(motion.x, motion.y - 0.05D * (amplifier + 1), motion.z);
            }
        }
    }
}
