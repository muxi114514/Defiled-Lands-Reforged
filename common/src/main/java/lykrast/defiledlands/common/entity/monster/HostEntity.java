package lykrast.defiledlands.common.entity.monster;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.IEntityDefiled;
import lykrast.defiledlands.common.registry.ModEntityTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class HostEntity extends Monster implements IEntityDefiled {
    private int slimesRemaining;
    private int slimesQueued;

    public HostEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.slimesRemaining = (int) this.getMaxHealth();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.27D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        spawnSlimes();
        super.aiStep();
    }

    @Override
    public void die(DamageSource cause) {
        queueSlimes(this.slimesRemaining);
        spawnSlimes();
        super.die(cause);
    }

    protected void queueSlimes(int nb) {
        if (this.slimesRemaining > 0) {
            int i = Math.min(nb, this.slimesRemaining);
            this.slimesRemaining -= i;
            this.slimesQueued += i;
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            boolean wasHurt = super.hurt(source, amount);
            if (wasHurt) {
                queueSlimes((int) amount);
            }
            return wasHurt;
        }
    }

    protected void spawnSlimes() {
        while (this.slimesQueued >= 2) {
            int size = 1;
            if (this.slimesQueued > 8) {
                size = 2;
            }

            if (!this.level().isClientSide) {
                int k = this.slimesQueued / 2;
                float f = ((float) (k % 2) - 0.5F) * 0.5F;
                float f1 = ((float) (k / 2) - 0.5F) * 0.5F;
                
                DefiledSlimeEntity slime = ModEntityTypes.SLIME_DEFILED.get().create(this.level());
                if (slime != null) {
                    if (this.isPersistenceRequired()) {
                        slime.setPersistenceRequired();
                    }
                    slime.setSize(size, true);
                    slime.moveTo(this.getX() + (double) f, this.getY() + 0.5D, this.getZ() + (double) f1, this.random.nextFloat() * 360.0F, 0.0F);
                    this.level().addFreshEntity(slime);
                }
            }

            if (size == 1) {
                this.slimesQueued -= 2;
            } else {
                this.slimesQueued -= size * 4;
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putShort("SlimesRemaining", (short) this.slimesRemaining);
        compound.putShort("SlimesQueued", (short) this.slimesQueued);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("SlimesRemaining", 99)) {
            this.slimesRemaining = compound.getShort("SlimesRemaining");
        }
        if (compound.contains("SlimesQueued", 99)) {
            this.slimesQueued = compound.getShort("SlimesQueued");
        }
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ZOMBIE_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(DefiledLands.MOD_ID, "entities/host");
    }
}
