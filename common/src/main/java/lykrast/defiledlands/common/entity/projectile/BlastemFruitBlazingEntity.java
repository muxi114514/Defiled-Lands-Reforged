package lykrast.defiledlands.common.entity.projectile;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

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

            result.getEntity().setSecondsOnFire(8);
        }
    }

    @Override
    protected void createExplosion(boolean isFlaming) {

        super.createExplosion(true);
    }
}
