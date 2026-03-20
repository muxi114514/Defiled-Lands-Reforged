package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.entity.projectile.BlastemFruitEntity;
import lykrast.defiledlands.common.registry.ModEntityTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Blastem Fruit 物品
 * 可以右键投掷，造成爆炸伤害
 */
public class BlastemFruitItem extends Item {
    
    public BlastemFruitItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        
        // 播放投掷音效
        level.playSound(null, player.getX(), player.getY(), player.getZ(), 
            SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 
            0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        
        // 设置冷却时间（1秒 = 20 tick）
        player.getCooldowns().addCooldown(this, 20);
        
        if (!level.isClientSide) {
            // 创建弹射物
            BlastemFruitEntity projectile = new BlastemFruitEntity(
                ModEntityTypes.BLASTEM_FRUIT.get(), 
                player, 
                level
            );
            
            // 设置弹射物的速度和方向
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            
            // 生成弹射物
            level.addFreshEntity(projectile);
        }
        
        // 统计
        player.awardStat(Stats.ITEM_USED.get(this));
        
        // 消耗物品（创造模式除外）
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }
}
