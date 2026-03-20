package lykrast.defiledlands.common.entity.passive;

import lykrast.defiledlands.common.entity.IEntityDefiled;
import lykrast.defiledlands.common.registry.ModItems;
import lykrast.defiledlands.common.util.CorruptionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BookWyrmEntity extends Animal implements IEntityDefiled {
    public static final ResourceLocation LOOT = new ResourceLocation("defiledlands", "entities/bookwyrm/normal");
    public static final ResourceLocation LOOT_GOLDEN = new ResourceLocation("defiledlands", "entities/bookwyrm/golden");
    
    private static final EntityDataAccessor<Boolean> GOLDEN = SynchedEntityData.defineId(BookWyrmEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DIGEST_TIME = SynchedEntityData.defineId(BookWyrmEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MAX_LEVEL = SynchedEntityData.defineId(BookWyrmEntity.class, EntityDataSerializers.INT);

    public int digested = 0;
    public int digesting = 0;
    public int digestTimer = 0;

    public BookWyrmEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(GOLDEN, false);
        this.entityData.define(DIGEST_TIME, 200);
        this.entityData.define(MAX_LEVEL, 3);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.26D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, Ingredient.of(Items.ENCHANTED_BOOK, ModItems.FOUL_CANDY.get()), false));
        this.goalSelector.addGoal(6, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, BookWyrmEntity.class));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        return entity.hurt(this.damageSources().mobAttack(this), 5.0F);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().getDifficulty() == net.minecraft.world.Difficulty.PEACEFUL) {
            if (this.getTarget() != null) this.setTarget(null);
            if (this.getLastHurtByMob() != null) this.setLastHurtByMob(null);
        }

        if (digesting > 0 && digestTimer > 0) {
            digestTimer--;

            if (this.level().isClientSide) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.ENCHANT,
                        this.getX() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(),
                        this.getY() + 0.25D + (double) (this.random.nextFloat() * this.getBbHeight()),
                        this.getZ() + (double) (this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double) this.getBbWidth(),
                        d0, d1, d2);
            }

            if (digestTimer <= 0) {
                digested++;
                digesting--;

                playDigestEffect(false);

                if (digesting > 0) digestTimer = getDigestTime();
            }
        }

        if (digested >= getMaxLevel()) {
            if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
                // 先尝试抽取符合过滤条件的附魔列表，最多尝试 10 次，避免消耗积累后无效产出
                List<EnchantmentInstance> list = java.util.Collections.emptyList();
                for (int attempt = 0; attempt < 10; attempt++) {
                    List<EnchantmentInstance> candidate = EnchantmentHelper.selectEnchantment(this.random, new ItemStack(Items.BOOK), getMaxLevel(), isGolden());
                    candidate.removeIf(e -> !CorruptionHelper.isWyrmEnchantAllowed(e.enchantment));
                    if (!candidate.isEmpty()) {
                        list = candidate;
                        break;
                    }
                }

                if (!list.isEmpty()) {
                    digested -= getMaxLevel();
                    this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    this.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
                    playDigestEffect(true);

                    list.sort(Comparator.comparingInt((e) -> -e.enchantment.getMinCost(e.level)));
                    int remaining = (int) (getMaxLevel() / 1.0); // 1.0 is default config conversionRate
                    for (EnchantmentInstance e : list) {
                        int value = (int) (e.enchantment.getMinCost(e.level) * 1.0);
                        if (remaining >= value) {
                            remaining -= value;
                            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                            EnchantedBookItem.addEnchantment(book, e);
                            this.spawnAtLocation(book, 0.5F);

                            if (remaining <= 0) break;
                        }
                    }
                }
            } else if (this.level().isClientSide) {
                // 客户端只做视觉判断，不负责实际产出
                digested -= getMaxLevel();
                this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.playSound(SoundEvents.PLAYER_LEVELUP, 1.0F, 1.0F);
                playDigestEffect(true);
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() == Items.ENCHANTED_BOOK && !this.isBaby()) {
            Map<Enchantment, Integer> list = EnchantmentHelper.getEnchantments(itemstack);

            if (list.isEmpty()) return super.mobInteract(player, hand);

            int i = 0;
            for (Map.Entry<Enchantment, Integer> e : list.entrySet()) {
                i += e.getKey().getMinCost(e.getValue());
            }

            i = (int) (i * 1.0); // conversionRate

            if (i > 0) {
                digesting += i;
                if (digestTimer == 0) digestTimer = getDigestTime();

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                this.playSound(SoundEvents.PLAYER_BURP, 1.0F, this.random.nextFloat() * 0.1F + 0.9F);
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(player, hand);
    }

    protected void playDigestEffect(boolean success) {
        if (!this.level().isClientSide) {
            ((ServerLevel) this.level()).sendParticles(success ? ParticleTypes.HAPPY_VILLAGER : ParticleTypes.SMOKE,
                    this.getX(), this.getY() + 0.5D, this.getZ(),
                    7,
                    this.getBbWidth() / 2.0F, this.getBbHeight() / 2.0F, this.getBbWidth() / 2.0F,
                    0.02D);
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        spawnData = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        
        setGolden(this.random.nextInt(100) == 0);
        setDigestTime(Mth.nextInt(this.random, 160, 240));
        setMaxLevel(Mth.nextInt(this.random, 3, 6));

        if (this.random.nextInt(5) == 0) {
            this.setAge(-24000);
        }

        return spawnData;
    }

    @Nullable
    @Override
    public BookWyrmEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        BookWyrmEntity child = lykrast.defiledlands.common.registry.ModEntityTypes.BOOK_WYRM.get().create(level);
        if (child != null && otherParent instanceof BookWyrmEntity parent) {
            boolean flag1 = isGolden();
            boolean flag2 = parent.isGolden();

            // 基础金色概率（金+金=10%, 金+普=4%, 普+普=1%）
            float baseChance;
            if (flag1 && flag2) baseChance = 0.1F;
            else if (flag1 || flag2) baseChance = 0.04F;
            else baseChance = 0.01F;

            // 幸运加成：取最近8格内繁殖玩家的 Luck 属性
            float luck = 0.0F;
            Player breeder = level.getNearestPlayer(this, 8.0);
            if (breeder != null) {
                AttributeInstance luckAttr = breeder.getAttribute(Attributes.LUCK);
                if (luckAttr != null) luck = (float) luckAttr.getValue();
            }

            float actualChance = baseChance;
            if (luck > 0 && CorruptionHelper.goldWyrmPerLuck > 0) {
                actualChance = baseChance * (1.0F + luck * (float) CorruptionHelper.goldWyrmPerLuck);
            }
            child.setGolden(this.random.nextFloat() < actualChance);

            int j1 = getDigestTime();
            int j2 = parent.getDigestTime();
            int k = j1 + j2 - this.random.nextInt((int) (Math.max(j1, j2) + 1 * 0.75));
            child.setDigestTime(k / 2);

            j1 = getMaxLevel();
            j2 = parent.getMaxLevel();
            k = j1 + j2 + this.random.nextInt(Math.max(j1, j2) + 1);
            child.setMaxLevel(Math.min(k / 2, CorruptionHelper.wyrmMaxLevelCap));
        }
        return child;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Golden", this.isGolden());
        compound.putInt("Digest", this.getDigestTime());
        compound.putInt("MaxLvl", this.getMaxLevel());
        compound.putInt("Digested", this.digested);
        compound.putInt("Digesting", this.digesting);
        compound.putInt("DigestTimer", this.digestTimer);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setGolden(compound.getBoolean("Golden"));
        setDigestTime(compound.getInt("Digest"));
        setMaxLevel(compound.getInt("MaxLvl"));
        digested = compound.getInt("Digested");
        digesting = compound.getInt("Digesting");
        digestTimer = compound.getInt("DigestTimer");
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == ModItems.FOUL_CANDY.get();
    }

    public boolean isGolden() {
        return this.entityData.get(GOLDEN);
    }

    public void setGolden(boolean golden) {
        this.entityData.set(GOLDEN, golden);
    }

    public int getDigestTime() {
        return this.entityData.get(DIGEST_TIME);
    }

    public void setDigestTime(int digest) {
        this.entityData.set(DIGEST_TIME, Math.max(digest, 1));
    }

    public int getMaxLevel() {
        return this.entityData.get(MAX_LEVEL);
    }

    public void setMaxLevel(int maxLevel) {
        this.entityData.set(MAX_LEVEL, Math.max(maxLevel, 1));
    }

    @Nullable
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return isGolden() ? LOOT_GOLDEN : LOOT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }
}
