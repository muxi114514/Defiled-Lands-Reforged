package lykrast.defiledlands;

import lykrast.defiledlands.common.registry.ModBlocks;
import lykrast.defiledlands.common.registry.ModEffects;
import lykrast.defiledlands.common.util.PlantUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DefiledLandsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = entity.getEffect(ModEffects.VULNERABILITY.get());
        if (effect != null) {
            float multiplier = 1.0f + (0.2f * (effect.getAmplifier() + 1));
            event.setAmount(event.getAmount() * multiplier);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.level instanceof ServerLevel level)) return;
        if (level.getGameTime() % 10 != 0) return;

        level.getAllEntities().forEach(entity -> {
            if (entity instanceof LivingEntity living && PlantUtils.vulnerableToBlastem(living)) {
                checkBlastemAround(level, living);
            }
        });
    }

    private static void checkBlastemAround(ServerLevel level, LivingEntity entity) {
        AABB box = entity.getBoundingBox().inflate(0.1);
        int minX = (int) Math.floor(box.minX);
        int minY = (int) Math.floor(box.minY);
        int minZ = (int) Math.floor(box.minZ);
        int maxX = (int) Math.floor(box.maxX);
        int maxY = (int) Math.floor(box.maxY);
        int maxZ = (int) Math.floor(box.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(ModBlocks.BLASTEM.get())) {
                        level.setBlock(pos, state, 2);
                        boolean mobGriefing = level.getGameRules().getBoolean(
                                net.minecraft.world.level.GameRules.RULE_MOBGRIEFING);
                        level.explode(null,
                                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                                1.0F, mobGriefing, Level.ExplosionInteraction.MOB);
                        return;
                    }
                }
            }
        }
    }
}
