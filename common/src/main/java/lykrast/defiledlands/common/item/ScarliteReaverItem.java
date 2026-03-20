package lykrast.defiledlands.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * Scarlite Reaver - 从活物中吸取生命
 */
public class ScarliteReaverItem extends SwordItem {

    public ScarliteReaverItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // 攻击生物时给予攻击者短暂的生命恢复效果
        if (target.isAlive() && attacker instanceof LivingEntity) {
            // 给予 0.15 秒（3 tick）的再生 XIX（19级）效果
            // 相当于瞬间恢复少量生命值
            attacker.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3, 18));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
