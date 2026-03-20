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

/**
 * Defilement Powder（污秽粉末）
 * 右键方块将其污化为污秽版本
 */
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

        // 检查玩家是否有权限编辑此位置
        if (player != null && !player.mayUseItemAt(pos, context.getClickedFace(), context.getItemInHand())) {
            return InteractionResult.FAIL;
        }

        // 尝试污化方块
        BlockState corrupted = CorruptionRecipes.getCorrupted(state);
        if (corrupted != null) {
            if (!level.isClientSide) {
                // 服务端：设置污化后的方块
                level.setBlock(pos, corrupted, 3);

                // 消耗物品（创造模式除外）
                if (player != null && !player.getAbilities().instabuild) {
                    context.getItemInHand().shrink(1);
                }
            } else {
                // 客户端：播放音效和粒子效果
                playCorruptionEffect(level, pos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.FAIL;
    }

    /**
     * 播放污化效果（粒子和音效）—— 仅客户端调用
     */
    private void playCorruptionEffect(Level level, BlockPos pos) {
        // 播放音效（null 表示对所有玩家可听）
        level.playLocalSound(
                pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS,
                0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F,
                false
        );

        // 生成 8 个烟雾粒子（客户端直接添加）
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
