package lykrast.defiledlands.common.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

/**
 * Scarlite Razor - 右击消耗生命补充饥饿度
 */
public class ScarliteRazorItem extends SwordItem {

    public ScarliteRazorItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        // 只有在可以吃东西时才能使用（饥饿度未满）
        if (player.canEat(false)) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            // 对玩家造成 3 点伤害
            if (player.hurt(level.damageSources().magic(), 3.0F)) {
                // 恢复 4 点饥饿值和 0.4 饱和度
                player.getFoodData().eat(4, 0.4F);
                
                // 播放打嗝音效
                level.playSound(null, player.getX(), player.getY(), player.getZ(), 
                    SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 
                    0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                
                // 损耗耐久
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            }
        }
        
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 12; // 使用时间（tick）
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT; // 使用吃东西的动画
    }
}
