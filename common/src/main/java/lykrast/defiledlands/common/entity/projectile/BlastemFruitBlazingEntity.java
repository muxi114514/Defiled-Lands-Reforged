package lykrast.defiledlands.common.entity.projectile;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

/**
 * Blazing Blastem Fruit 弹射物
 * 燃烧版本，更强的爆炸和火焰效果
 */
public class BlastemFruitBlazingEntity extends BlastemFruitEntity {

    public BlastemFruitBlazingEntity(EntityType<? extends BlastemFruitBlazingEntity> type, Level level) {
        super(type, level);
        this.damage = 10.0F;
        this.explosionStrength = 1.5F;
        this.destructive = true;
    }

    public BlastemFruitBlazingEntity(EntityType<? extends BlastemFruitBlazingEntity> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.damage = 10.0F;
        this.explosionStrength = 1.5F;
        this.destructive = true;
    }

    public BlastemFruitBlazingEntity(EntityType<? extends BlastemFruitBlazingEntity> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
        this.damage = 10.0F;
        this.explosionStrength = 1.5F;
        this.destructive = true;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BLASTEM_FRUIT_BLAZING.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            // 点燃被击中的实体 8 秒
            result.getEntity().setSecondsOnFire(8);
        }
    }

    @Override
    protected void createExplosion(boolean isFlaming) {
        // 燃烧版本总是产生火焰爆炸
        super.createExplosion(true);
    }
}
