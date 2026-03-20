package lykrast.defiledlands.common.entity.monster;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

public class ShamblerEntity extends Monster implements IEntityDefiled {

    public ShamblerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.14D)
                .add(Attributes.ATTACK_DAMAGE, 14.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 64.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean flag = super.doHurtTarget(entity);
        if (flag) {
            float force = 5.0F * 0.5F;
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.knockback(force, Math.sin(this.getYRot() * ((float) Math.PI / 180F)),
                        -Math.cos(this.getYRot() * ((float) Math.PI / 180F)));

                int duration = getDebuffDuration();
                if (duration > 0) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 1));
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, duration, 1));
                }
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, 0));
            } else {
                entity.push(-Math.sin(this.getYRot() * ((float) Math.PI / 180F)) * force, 0.1D,
                        Math.cos(this.getYRot() * ((float) Math.PI / 180F)) * force);
            }
        }
        return flag;
    }

    protected int getDebuffDuration() {
        int i = 3;
        if (this.level().getDifficulty() == Difficulty.NORMAL) {
            i = 15;
        } else if (this.level().getDifficulty() == Difficulty.HARD) {
            i = 30;
        }
        return i * 20;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (source.is(DamageTypeTags.IS_PROJECTILE)) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void aiStep() {
        this.setJumping(false);

        if (!this.level().isClientSide && this.level().isDay()) {
            float f = this.getLightLevelDependentMagicValue();
            if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F
                    && this.level().canSeeSky(BlockPos.containing(this.getX(), this.getEyeY(), this.getZ()))) {
                this.discard();
            }
        }

        super.aiStep();
    }

    @Override
    public MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    protected float getStandingEyeHeight(net.minecraft.world.entity.Pose pose,
            net.minecraft.world.entity.EntityDimensions dimensions) {
        return 2.55F;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(DefiledLands.MOD_ID, "entities/shambler");
    }
}
