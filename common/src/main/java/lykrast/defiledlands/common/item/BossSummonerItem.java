package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.block.ConjuringAltarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class BossSummonerItem extends Item {
    private final Supplier<? extends EntityType<?>> bossType;

    public BossSummonerItem(Supplier<? extends EntityType<?>> bossType, Properties properties) {
        super(properties.stacksTo(1));
        this.bossType = bossType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();

        if (state.getBlock() instanceof ConjuringAltarBlock && state.getValue(ConjuringAltarBlock.ACTIVE)) {
            if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                Entity boss = bossType.get().create(serverLevel);
                if (boss != null) {
                    boss.moveTo(pos.getX() + 0.5, pos.getY() + 3.0, pos.getZ() + 0.5, 0.0F, 0.0F);
                    if (boss instanceof lykrast.defiledlands.common.entity.boss.DestroyerEntity destroyer) {
                        destroyer.ignite();
                    } else if (boss instanceof lykrast.defiledlands.common.entity.boss.MournerEntity mourner) {
                        mourner.ignite();
                    }
                    serverLevel.addFreshEntity(boss);
                }
            }
            if (player != null && !player.isCreative()) {
                itemstack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);

    }
}
