package lykrast.defiledlands.common.entity.monster;

import lykrast.defiledlands.common.DefiledLands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class TwistedShamblerEntity extends ShamblerEntity {

    public TwistedShamblerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return ShamblerEntity.createAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.28D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Replace previous nearest attackable target goal with one that has
        // mustSee=false
        this.targetSelector.removeAllGoals(goal -> goal instanceof NearestAttackableTargetGoal);
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return new ResourceLocation(DefiledLands.MOD_ID, "entities/shambler_twisted");
    }
}
