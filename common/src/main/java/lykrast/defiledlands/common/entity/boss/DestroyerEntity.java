package lykrast.defiledlands.common.entity.boss;

import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.EnumSet;

public class DestroyerEntity extends Monster implements IEntityDefiled {
    private static final EntityDataAccessor<Integer> INVULNERABILITY_TIME = SynchedEntityData.defineId(DestroyerEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> LEAPING = SynchedEntityData.defineId(DestroyerEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    public DestroyerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 50;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(INVULNERABILITY_TIME, 0);
        this.entityData.define(LEAPING, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.32D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BigLeapGoal(this, 1.2F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public int getInvulTime() {
        return this.entityData.get(INVULNERABILITY_TIME);
    }

    public void setInvulTime(int time) {
        this.entityData.set(INVULNERABILITY_TIME, time);
    }

    public boolean isLeaping() {
        return this.entityData.get(LEAPING);
    }

    public void setLeaping(boolean val) {
        this.entityData.set(LEAPING, val);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        if (isLeaping()) {
            setLeaping(false);
            if (!this.level().isClientSide) {
                boolean mobGriefing = this.level().getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, mobGriefing ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
            }
        }
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (super.doHurtTarget(entity)) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.4D, 0));
            return true;
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.getInvulTime() > 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).add(0.0D, -0.01D, 0.0D));
            this.setNoGravity(true);
            if (this.level().isClientSide) {

            }
        } else {
            this.setNoGravity(false);
        }
        if (isLeaping() && isInWater()) {
            setLeaping(false);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (this.getInvulTime() > 0) {
            int j1 = this.getInvulTime() - 1;
            if (j1 <= 0) {
                boolean mobGriefing = this.level().getGameRules().getBoolean(net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                this.level().explode(this, this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ(), 7.0F, mobGriefing ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
                this.playSound(SoundEvents.WITHER_SPAWN, 10.0F, 1.0F);
            }
            this.setInvulTime(j1);
        } else {
            super.customServerAiStep();
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }
    }

    public void ignite() {
        this.setInvulTime(200);
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!source.is(DamageTypes.DROWN) && !(source.getEntity() instanceof DestroyerEntity)) {
            if (this.getInvulTime() > 0 && !source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return false;
            }
            return super.hurt(source, amount);
        }
        return false;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime >= 180 && this.deathTime <= 200) {

        }
        if (!this.level().isClientSide) {
            if (this.deathTime == 1) {
                this.playSound(SoundEvents.ENDER_DRAGON_DEATH, 10.0F, 1.0F);
            }
        }
        this.setDeltaMovement(0, 0.01, 0);
        if (this.deathTime >= 200 && !this.level().isClientSide) {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Invul", this.getInvulTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setInvulTime(compound.getInt("Invul"));
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    static class BigLeapGoal extends Goal {
        private final DestroyerEntity leaper;
        private LivingEntity leapTarget;
        private final float leapMotionY;

        public BigLeapGoal(DestroyerEntity leapingEntity, float leapMotionYIn) {
            this.leaper = leapingEntity;
            this.leapMotionY = leapMotionYIn;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            this.leapTarget = this.leaper.getTarget();
            if (this.leapTarget == null || !this.leaper.onGround() || this.leaper.isInWater()) {
                return false;
            } else {
                double d0 = this.leaper.distanceToSqr(this.leapTarget);
                return d0 >= 16.0D && d0 <= 1024.0D && this.leaper.getRandom().nextInt(7) == 0;
            }
        }

        @Override
        public void start() {
            double d0 = this.leapTarget.getX() - this.leaper.getX();
            double d1 = this.leapTarget.getZ() - this.leaper.getZ();
            float f = (float) Math.sqrt(d0 * d0 + d1 * d1);
            float enrage = (1.0F - this.leaper.getHealth() / this.leaper.getMaxHealth()) * 1.0F + 1.0F;

            if (f >= 1.0E-4D) {
                this.leaper.setDeltaMovement(this.leaper.getDeltaMovement().add(d0 / (double) f * 1.5D * 0.8D, 0.0D, d1 / (double) f * 1.5D * 0.8D));
            }
            this.leaper.setDeltaMovement(this.leaper.getDeltaMovement().multiply(enrage, 1.0, enrage).add(0.0D, this.leapMotionY, 0.0D));
            this.leaper.setLeaping(true);
        }
    }

    static class DoNothingGoal extends Goal {
        private final DestroyerEntity destroyer;

        public DoNothingGoal(DestroyerEntity destroyer) {
            this.destroyer = destroyer;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.destroyer.getInvulTime() > 0;
        }
    }
}
