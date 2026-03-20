package lykrast.defiledlands.common.block.entity;

import lykrast.defiledlands.common.block.ConjuringAltarBlock;
import lykrast.defiledlands.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ConjuringAltarBlockEntity extends BlockEntity {
    public static final TagKey<Biome> IS_DEFILED = TagKey.create(Registries.BIOME, new ResourceLocation("defiledlands", "is_defiled"));
    private boolean active;

    public ConjuringAltarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONJURING_ALTAR.get(), pos, state);
        this.active = false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ConjuringAltarBlockEntity entity) {
        if (!level.isClientSide) {
            entity.recalculate(state);
        }
    }

    public void recalculate(BlockState state) {
        boolean flag1 = level.getBlockState(worldPosition.above()).isAir() && level.getBlockState(worldPosition.above(2)).isAir();
        boolean result = flag1;

        if (result != active) {
            this.active = result;
            setChanged();
            if (state.hasProperty(ConjuringAltarBlock.ACTIVE) && state.getValue(ConjuringAltarBlock.ACTIVE) != active) {
                level.setBlock(worldPosition, state.setValue(ConjuringAltarBlock.ACTIVE, active), 3);
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        active = compound.getBoolean("active");
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putBoolean("active", active);
    }
}
