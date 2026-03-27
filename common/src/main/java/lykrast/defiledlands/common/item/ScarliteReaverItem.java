package lykrast.defiledlands.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ScarliteReaverItem extends SwordItem {

    public ScarliteReaverItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (target.isAlive() && attacker instanceof LivingEntity) {

            attacker.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3, 18));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
