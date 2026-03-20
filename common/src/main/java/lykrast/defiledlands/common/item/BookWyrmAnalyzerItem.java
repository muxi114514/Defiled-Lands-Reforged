package lykrast.defiledlands.common.item;

import lykrast.defiledlands.common.entity.passive.BookWyrmEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BookWyrmAnalyzerItem extends Item {
    public BookWyrmAnalyzerItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity interactionTarget, InteractionHand usedHand) {
        if (interactionTarget.level().isClientSide) {
            return InteractionResult.PASS; // Return PASS on client side for right-click on entities
        }

        if (interactionTarget instanceof BookWyrmEntity target) {
            String base = "ui.defiledlands.book_wyrm_analyze.";
            player.sendSystemMessage(Component.translatable(base + "health", (int) target.getHealth(), (int) target.getMaxHealth()));
            player.sendSystemMessage(Component.translatable(base + "digest_time", target.getDigestTime()));
            player.sendSystemMessage(Component.translatable(base + "max_level", target.getMaxLevel()));
            player.sendSystemMessage(Component.translatable(base + "digested", target.digested));
            
            if (target.digesting > 0) {
                player.sendSystemMessage(Component.translatable(base + "digesting", target.digesting));
            }
            
            if (target.isBaby()) {
                int minutes = (int) Math.ceil((-target.getAge()) / 1200.0D);
                player.sendSystemMessage(Component.translatable(base + "maturing", minutes));
            } else if (target.getAge() > 0) {
                int minutes = (int) Math.ceil(target.getAge() / 1200.0D);
                player.sendSystemMessage(Component.translatable(base + "reproduce", minutes));
            } else {
                player.sendSystemMessage(Component.translatable(base + "ready"));
            }
            
            if (target.isGolden()) {
                player.sendSystemMessage(Component.translatable(base + "golden"));
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
        tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".tooltip").withStyle(net.minecraft.ChatFormatting.GRAY));
    }
}
