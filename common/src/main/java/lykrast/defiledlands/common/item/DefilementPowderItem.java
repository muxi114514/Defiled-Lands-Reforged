package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.util.CorruptionRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DefilementPowderItem extends Item {

    public DefilementPowderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        BlockState state = level.getBlockState(pos);

        if (player != null && !player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand())) {
            return InteractionResult.FAIL;
        }

        BlockState corrupted = CorruptionRecipes.getCorrupted(state);
        if (corrupted != null) {
            if (!level.isClientSide) {

                level.setBlock(pos, corrupted, 3);

                if (player != null && !player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
            } else {

                playCorruptionEffect(level, pos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.FAIL;
    }

    private void playCorruptionEffect(Level level, BlockPos pos) {

        level.playLocalSound(
                pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS,
                0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F,
                false
        );

        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        for (int i = 0; i < 8; i++) {
            level.addParticle(
                    ParticleTypes.LARGE_SMOKE,
                    x + level.random.nextDouble(),
                    y + 1.2D,
                    z + level.random.nextDouble(),
                    0.0D, 0.0D, 0.0D
            );
        }
    }
}
