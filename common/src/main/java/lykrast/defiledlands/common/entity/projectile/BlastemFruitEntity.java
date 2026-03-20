package lykrast.defiledlands.common.entity.projectile;

import lykrast.defiledlands.common.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * Blastem Fruit 弹射物
 * 投掷后爆炸，造成伤害
 */
public class BlastemFruitEntity extends ThrowableItemProjectile {
    // 使用普通字段存储，避免 entityData 初始化顺序问题
    protected float damage = 7.0F;
    protected float explosionStrength = 1.0F;
    protected boolean destructive = true;

    public BlastemFruitEntity(EntityType<? extends BlastemFruitEntity> type, Level level) {
        super(type, level);
    }

    public BlastemFruitEntity(EntityType<? extends BlastemFruitEntity> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
    }

    public BlastemFruitEntity(EntityType<? extends BlastemFruitEntity> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BLASTEM_FRUIT.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!this.level().isClientSide) {
            result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), damage);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            createExplosion(false);
            this.discard();
        }
    }

    /**
     * 创建爆炸（仅服务端调用）
     */
    protected void createExplosion(boolean isFlaming) {
        Level level = this.level();
        level.explode(
            this,
            this.getX(),
            this.getY(),
            this.getZ(),
            explosionStrength,
            isFlaming,
            destructive ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE
        );
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putFloat("damage", damage);
        compound.putFloat("explosion", explosionStrength);
        compound.putBoolean("destructive", destructive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("damage")) {
            damage = compound.getFloat("damage");
        }
        if (compound.contains("explosion")) {
            explosionStrength = compound.getFloat("explosion");
        }
        if (compound.contains("destructive")) {
            destructive = compound.getBoolean("destructive");
        }
    }

    public void setDamage(float damage) { this.damage = damage; }
    public float getDamage() { return damage; }
    public void setExplosionStrength(float explosion) { this.explosionStrength = explosion; }
    public float getExplosionStrength() { return explosionStrength; }
    public void setDestructive(boolean destructive) { this.destructive = destructive; }
    public boolean isDestructive() { return destructive; }
}
