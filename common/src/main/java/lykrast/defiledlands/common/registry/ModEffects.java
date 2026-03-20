package lykrast.defiledlands.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.effect.BleedingEffect;
import lykrast.defiledlands.common.effect.GroundedEffect;
import lykrast.defiledlands.common.effect.VulnerabilityEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(DefiledLands.MOD_ID, Registries.MOB_EFFECT);

    /**
     * Vulnerability（脆弱）- 受到更多伤害
     * 每级增加 20% 伤害
     */
    public static final RegistrySupplier<MobEffect> VULNERABILITY = EFFECTS.register("vulnerability", VulnerabilityEffect::new);

    /**
     * Grounded（沉重）- 无法跳跃，快速下坠
     */
    public static final RegistrySupplier<MobEffect> GROUNDED = EFFECTS.register("grounded", GroundedEffect::new);

    /**
     * Bleeding（出血）- 持续伤害，可堆叠
     * 每 2 秒造成 (等级+1) 点伤害
     */
    public static final RegistrySupplier<MobEffect> BLEEDING = EFFECTS.register("bleeding", BleedingEffect::new);

    public static void register() {
        EFFECTS.register();
    }
}
