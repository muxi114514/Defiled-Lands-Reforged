package lykrast.defiledlands.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.block.entity.ConjuringAltarBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(DefiledLands.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<ConjuringAltarBlockEntity>> CONJURING_ALTAR = BLOCK_ENTITIES.register("conjuring_altar", () ->
            BlockEntityType.Builder.of(ConjuringAltarBlockEntity::new, ModBlocks.CONJURING_ALTAR.get()).build(null)
    );

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
