package lykrast.defiledlands.common.entity.monster;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

public class DefiledSlimeEntity extends Slime implements IEntityDefiled {

    public DefiledSlimeEntity(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        if (spawnType == MobSpawnType.SPAWNER) return true;
        BlockPos pos = this.blockPosition();

        return level.getMaxLocalRawBrightness(pos) <= 7;
    }

    @Override
    public void setSize(int size, boolean resetHealth) {
        super.setSize(size, resetHealth);

    }

    @Override
    protected int getJumpDelay() {
        return this.random.nextInt(10) + 5;
    }

    @Override
    public boolean isDealsDamage() {

        return true;
    }

    @Override
    protected float getAttackDamage() {
        return (float) (this.getSize() * 2);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {

    }

    @Override
    protected void jumpFromGround() {
        super.jumpFromGround();
        this.setDeltaMovement(this.getDeltaMovement().x, 0.6D, this.getDeltaMovement().z);
    }

    @Override
    @Nullable
    protected ResourceLocation getDefaultLootTable() {
        return this.getSize() == 1 ? new ResourceLocation(DefiledLands.MOD_ID, "entities/slime_defiled") : null;
    }
}
