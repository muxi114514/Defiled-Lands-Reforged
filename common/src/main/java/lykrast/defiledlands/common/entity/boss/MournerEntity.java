package lykrast.defiledlands.common.entity.boss;

import lykrast.defiledlands.common.entity.IEntityDefiled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MournerEntity extends Monster implements IEntityDefiled {
    private static final EntityDataAccessor<Integer> INVULNERABILITY_TIME = SynchedEntityData
            .defineId(MournerEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Byte> MOURNER_FLAGS = SynchedEntityData.defineId(MournerEntity.class,
            EntityDataSerializers.BYTE);

    private final ServerBossEvent bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

    public MournerEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 200;
        this.moveControl = new MournerMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 400.0D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 128.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(INVULNERABILITY_TIME, 0);
        this.entityData.define(MOURNER_FLAGS, (byte) 0);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DoNothingGoal(this));
        this.goalSelector.addGoal(3, new MournerChargeGoal(this));
        this.goalSelector.addGoal(4, new MournerRangedAttackGoal(this));
        this.goalSelector.addGoal(8, new MournerRandomFlyGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public int getInvulTime() {
        return this.entityData.get(INVULNERABILITY_TIME);
    }

    public void setInvulTime(int time) {
        this.entityData.set(INVULNERABILITY_TIME, time);
    }

    public boolean isCharging() {
        return (this.entityData.get(MOURNER_FLAGS) & 1) != 0;
    }

    public void setCharging(boolean value) {
        byte b = this.entityData.get(MOURNER_FLAGS);
        if (value)
            b |= 1;
        else
            b &= ~1;
        this.entityData.set(MOURNER_FLAGS, b);
    }

    public boolean isFiring() {
        return (this.entityData.get(MOURNER_FLAGS) & 2) != 0;
    }

    public void setFiring(boolean value) {
        byte b = this.entityData.get(MOURNER_FLAGS);
        if (value)
            b |= 2;
        else
            b &= ~2;
        this.entityData.set(MOURNER_FLAGS, b);
    }

    public int getCurrentAttack() {
        return (this.entityData.get(MOURNER_FLAGS) >> 2) & 3;
    }

    public void setCurrentAttack(int value) {
        int i = this.entityData.get(MOURNER_FLAGS);
        i &= 0x3;
        i |= (value << 2);
        this.entityData.set(MOURNER_FLAGS, (byte) i);
    }

    public int getRageFactor() {
        float h = getHealth();
        if (h < 100)
            return 3;
        else if (h < 200)
            return 2;
        else
            return 1;
    }

    // noPhysics 允许穿墙移动，对应原版 noClip
    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (getInvulTime() > 0) {
            this.setDeltaMovement(0, 0.01, 0);

            // 无敌期客户端粒子
            if (this.level().isClientSide) {
                for (int i = 0; i < 3; i++) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT,
                            this.getX() + this.random.nextGaussian(),
                            this.getY() + this.random.nextFloat() * 3.3F,
                            this.getZ() + this.random.nextGaussian(),
                            0.7D, 0.7D, 0.9D);
                }
            } else {
                // 无敌期服务端闪电特效
                int invul = getInvulTime();
                if ((invul > 60 && invul % 40 == 0) || (invul <= 60 && invul % 10 == 0)) {
                    int y = (int) this.getY() + this.random.nextInt(11);
                    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(
                            (int) this.getX() + this.random.nextInt(15) - 7,
                            y,
                            (int) this.getZ() + this.random.nextInt(15) - 7);
                    while (pos.getY() > this.level().getMinBuildHeight() && y - pos.getY() < 16
                            && this.level().isEmptyBlock(pos)) {
                        pos.setY(pos.getY() - 1);
                    }
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level());
                    if (bolt != null) {
                        bolt.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
                        bolt.setVisualOnly(true);
                        this.level().addFreshEntity(bolt);
                    }
                }
            }
        }

        // 攻击时客户端粒子效果
        if (this.level().isClientSide && isFiring()) {
            var particle = switch (getCurrentAttack()) {
                case ATTACK_FIREBALLS -> ParticleTypes.FLAME;
                case ATTACK_SHULKER -> ParticleTypes.END_ROD;
                case ATTACK_GHAST -> ParticleTypes.LARGE_SMOKE;
                default -> ParticleTypes.LARGE_SMOKE;
            };
            for (int i = 0; i < 2; i++) {
                this.level().addParticle(particle,
                        this.getX() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                        this.getY() + this.random.nextDouble() * this.getBbHeight(),
                        this.getZ() + (this.random.nextDouble() - 0.5D) * this.getBbWidth(),
                        0, 0, 0);
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        if (this.getInvulTime() > 0) {
            int j1 = this.getInvulTime() - 1;
            if (j1 <= 0) {
                boolean mobGriefing = this.level().getGameRules()
                        .getBoolean(net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                this.level().explode(this, this.getX(), this.getY() + (double) this.getEyeHeight(), this.getZ(), 7.0F,
                        mobGriefing ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE);
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
        } else if (!source.is(DamageTypes.DROWN) && !(source.getEntity() instanceof MournerEntity)) {
            if (this.getInvulTime() > 0 && !source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
                return false;
            }
            return super.hurt(source, amount);
        }
        return false;
    }

    // 近战攻击：强力击退 + 上抛
    @Override
    public boolean doHurtTarget(Entity target) {
        if (super.doHurtTarget(target)) {
            target.setDeltaMovement(target.getDeltaMovement().add(0, 0.4, 0));
            if (target instanceof LivingEntity living) {
                living.knockback(3.0F,
                        Mth.sin(this.getYRot() * ((float) Math.PI / 180F)),
                        -Mth.cos(this.getYRot() * ((float) Math.PI / 180F)));
            }
            return true;
        }
        return false;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        this.setDeltaMovement(0, 0.01, 0);

        // 死亡爆炸粒子（180-200 tick）
        if (this.deathTime >= 180 && this.deathTime <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.level().addParticle(ParticleTypes.EXPLOSION_EMITTER,
                    this.getX() + f, this.getY() + 2.0D + f1, this.getZ() + f2,
                    0, 0, 0);
        }

        boolean doLoot = this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);

        if (!this.level().isClientSide) {
            // 死亡音效
            if (this.deathTime == 1) {
                this.playSound(SoundEvents.ENDER_DRAGON_DEATH, 10.0F, 1.0F);
            }
            // 分阶段经验掉落
            if (this.deathTime > 150 && this.deathTime % 5 == 0 && doLoot) {
                dropExperience(Mth.floor(this.xpReward * 0.08F));
            }
        }

        if (this.deathTime >= 200) {
            if (!this.level().isClientSide) {
                if (doLoot) {
                    dropExperience(Mth.floor(this.xpReward * 0.2F));
                }
                this.remove(RemovalReason.KILLED);
            }
            // 死亡最终粒子
            for (int k = 0; k < 20; k++) {
                this.level().addParticle(ParticleTypes.POOF,
                        this.getX() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                        this.getY() + this.random.nextFloat() * this.getBbHeight(),
                        this.getZ() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth(),
                        this.random.nextGaussian() * 0.02D,
                        this.random.nextGaussian() * 0.02D,
                        this.random.nextGaussian() * 0.02D);
            }
        }
    }

    private void dropExperience(int amount) {
        while (amount > 0) {
            int split = ExperienceOrb.getExperienceValue(amount);
            amount -= split;
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY(), this.getZ(), split));
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

    public boolean attemptTeleportAir(double x, double y, double z) {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        this.setPos(x, y, z);
        boolean flag = false;

        if (this.level().noCollision(this)) {
            flag = true;
        }

        if (!flag) {
            this.setPos(d0, d1, d2);
            return false;
        } else {
            this.level().playSound(null, d0, d1, d2, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
            this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            return true;
        }
    }

    static class DoNothingGoal extends Goal {
        private final MournerEntity mourner;

        public DoNothingGoal(MournerEntity mourner) {
            this.mourner = mourner;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return this.mourner.getInvulTime() > 0;
        }
    }

    static class MournerMoveControl extends MoveControl {
        private final MournerEntity mourner;

        public MournerMoveControl(MournerEntity mourner) {
            super(mourner);
            this.mourner = mourner;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - mourner.getX(), this.wantedY - mourner.getY(),
                        this.wantedZ - mourner.getZ());
                double d0 = vec3.length();
                if (d0 < mourner.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    mourner.setDeltaMovement(mourner.getDeltaMovement().scale(0.5D));
                } else if (d0 >= 20 && mourner.attemptTeleportAir(wantedX, wantedY, wantedZ)) {
                    this.operation = Operation.WAIT;
                } else {
                    mourner.setDeltaMovement(
                            mourner.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05D / d0)));
                    if (mourner.getTarget() == null) {
                        Vec3 vec31 = mourner.getDeltaMovement();
                        mourner.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float) Math.PI));
                        mourner.yBodyRot = mourner.getYRot();
                    } else {
                        double d1 = mourner.getTarget().getX() - mourner.getX();
                        double d2 = mourner.getTarget().getZ() - mourner.getZ();
                        mourner.setYRot(-((float) Mth.atan2(d1, d2)) * (180F / (float) Math.PI));
                        mourner.yBodyRot = mourner.getYRot();
                    }
                }
            }
        }
    }

    static class MournerChargeGoal extends Goal {
        private final MournerEntity mourner;

        public MournerChargeGoal(MournerEntity mourner) {
            this.mourner = mourner;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return mourner.getTarget() != null && mourner.getTarget().isAlive()
                    && mourner.distanceToSqr(mourner.getTarget()) <= 16.0D;
        }

        @Override
        public boolean canContinueToUse() {
            return mourner.getMoveControl().hasWanted() && mourner.isCharging() && mourner.getTarget() != null
                    && mourner.getTarget().isAlive() && mourner.distanceToSqr(mourner.getTarget()) <= 25.0D;
        }

        @Override
        public void start() {
            LivingEntity target = mourner.getTarget();
            if (target != null) {
                Vec3 vec3 = target.getEyePosition(1.0F);
                mourner.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z,
                        0.5D * (mourner.getRageFactor() + 1));
                mourner.setCharging(true);
                mourner.setFiring(false);
                mourner.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
            }
        }

        @Override
        public void stop() {
            mourner.setCharging(false);
        }

        @Override
        public void tick() {
            LivingEntity target = mourner.getTarget();
            if (target == null)
                return;
            if (mourner.getBoundingBox().inflate(0.5D).intersects(target.getBoundingBox())) {
                mourner.doHurtTarget(target);
                mourner.swing(InteractionHand.MAIN_HAND);
                mourner.setCharging(false);
            } else {
                Vec3 vec3 = target.getEyePosition(1.0F);
                mourner.getMoveControl().setWantedPosition(vec3.x, vec3.y - 1.0D, vec3.z,
                        0.5D * (mourner.getRageFactor() + 1));
            }
        }
    }

    public static final int ATTACK_FIREBALLS = 0, ATTACK_SHULKER = 1, ATTACK_GHAST = 2;

    static class MournerRangedAttackGoal extends Goal {
        private final MournerEntity mourner;
        private int attackStep;
        private int attackTime;

        public MournerRangedAttackGoal(MournerEntity mourner) {
            this.mourner = mourner;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return mourner.getTarget() != null && !mourner.getMoveControl().hasWanted() && !mourner.isCharging()
                    && mourner.getRandom().nextInt(9 - mourner.getRageFactor() * 2) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return attackStep > 0 && mourner.isFiring() && mourner.getTarget() != null && mourner.getTarget().isAlive();
        }

        @Override
        public void start() {
            attackStep = 2 + mourner.getRageFactor() * 3;
            attackTime = 60;
            mourner.setFiring(true);
            mourner.setCurrentAttack(mourner.getRandom().nextInt(3));
        }

        @Override
        public void stop() {
            mourner.setFiring(false);
        }

        @Override
        public void tick() {
            LivingEntity target = mourner.getTarget();
            if (target == null)
                return;
            --attackTime;
            if (attackTime <= 0) {
                --attackStep;
                attackTime = 3;
                Level level = mourner.level();
                switch (mourner.getCurrentAttack()) {
                    case ATTACK_FIREBALLS:
                        double d0 = target.getX() - mourner.getX();
                        double d1 = target.getY(0.5D) - mourner.getY(0.5D);
                        double d2 = target.getZ() - mourner.getZ();
                        SmallFireball smallfireball = new SmallFireball(level, mourner,
                                d0 + mourner.getRandom().nextGaussian(), d1, d2 + mourner.getRandom().nextGaussian());
                        smallfireball.setPos(mourner.getX(), mourner.getY(0.5D) + 0.5D, mourner.getZ());
                        level.addFreshEntity(smallfireball);
                        level.levelEvent(null, 1018, mourner.blockPosition(), 0);
                        break;
                    case ATTACK_SHULKER:
                        ShulkerBullet shulkerbullet = new ShulkerBullet(level, mourner, target,
                                mourner.getDirection().getAxis());
                        shulkerbullet.setPos(mourner.getX(), mourner.getY(0.5D) + 0.5D, mourner.getZ());
                        level.addFreshEntity(shulkerbullet);
                        mourner.playSound(SoundEvents.SHULKER_SHOOT, 2.0F,
                                (mourner.getRandom().nextFloat() - mourner.getRandom().nextFloat()) * 0.2F + 1.0F);
                        break;
                    case ATTACK_GHAST:
                        Vec3 vec3 = mourner.getViewVector(1.0F);
                        LargeFireball largefireball = new LargeFireball(level, mourner,
                                target.getX() - (mourner.getX() + vec3.x * 4.0D),
                                target.getY(0.5D) - mourner.getY(1.0D),
                                target.getZ() - (mourner.getZ() + vec3.z * 4.0D), mourner.getRageFactor());
                        largefireball.setPos(mourner.getX(), mourner.getY() + (double) mourner.getBbHeight() + 0.5D,
                                mourner.getZ());
                        level.addFreshEntity(largefireball);
                        level.levelEvent(null, 1016, mourner.blockPosition(), 0);
                        // 恶魂火球额外冷却，与原版一致
                        --attackStep;
                        attackTime = 10;
                        break;
                }
            }
            mourner.getLookControl().setLookAt(target, 10.0F, 10.0F);
        }
    }

    static class MournerRandomFlyGoal extends Goal {
        private final MournerEntity mourner;

        public MournerRandomFlyGoal(MournerEntity mourner) {
            this.mourner = mourner;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !mourner.getMoveControl().hasWanted() && mourner.getRandom().nextInt(7) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void tick() {
            BlockPos targetPos;
            if (mourner.getTarget() != null && mourner.getTarget().isAlive()) {
                targetPos = mourner.getTarget().blockPosition();
            } else {
                targetPos = mourner.blockPosition();
            }
            for (int i = 0; i < 3; ++i) {
                BlockPos blockpos = targetPos.offset(mourner.getRandom().nextInt(15) - 7,
                        mourner.getRandom().nextInt(11), mourner.getRandom().nextInt(15) - 7);
                if (mourner.level().isEmptyBlock(blockpos)) {
                    mourner.getMoveControl().setWantedPosition(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D,
                            blockpos.getZ() + 0.5D, 0.25D);
                    break;
                }
            }
        }
    }
}
