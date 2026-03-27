package lykrast.defiledlands.common.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.entity.monster.*;
import lykrast.defiledlands.common.entity.projectile.BlastemFruitBlazingEntity;
import lykrast.defiledlands.common.entity.projectile.BlastemFruitEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(DefiledLands.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ShamblerEntity>> SHAMBLER = ENTITIES.register("shambler", () ->
            EntityType.Builder.of(ShamblerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build("shambler"));

    public static final RegistrySupplier<EntityType<TwistedShamblerEntity>> SHAMBLER_TWISTED = ENTITIES.register("shambler_twisted", () ->
            EntityType.Builder.of(TwistedShamblerEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8)
                    .build("shambler_twisted"));

    public static final RegistrySupplier<EntityType<ScuttlerEntity>> SCUTTLER = ENTITIES.register("scuttler", () ->
            EntityType.Builder.of(ScuttlerEntity::new, MobCategory.MONSTER)
                    .sized(0.85F, 0.65F)
                    .clientTrackingRange(8)
                    .build("scuttler"));

    public static final RegistrySupplier<EntityType<HostEntity>> HOST = ENTITIES.register("host", () ->
            EntityType.Builder.of(HostEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F)
                    .clientTrackingRange(8)
                    .build("host"));

    public static final RegistrySupplier<EntityType<DefiledSlimeEntity>> SLIME_DEFILED = ENTITIES.register("slime_defiled", () ->
            EntityType.Builder.of(DefiledSlimeEntity::new, MobCategory.MONSTER)
                    .sized(2.04F, 2.04F)
                    .clientTrackingRange(10)
                    .build("slime_defiled"));

    public static final RegistrySupplier<EntityType<lykrast.defiledlands.common.entity.boss.DestroyerEntity>> DESTROYER = ENTITIES.register("the_destroyer", () ->
            EntityType.Builder.of(lykrast.defiledlands.common.entity.boss.DestroyerEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.4F)
                    .clientTrackingRange(10)
                    .build("the_destroyer"));

    public static final RegistrySupplier<EntityType<lykrast.defiledlands.common.entity.boss.MournerEntity>> MOURNER = ENTITIES.register("the_mourner", () ->
            EntityType.Builder.of(lykrast.defiledlands.common.entity.boss.MournerEntity::new, MobCategory.MONSTER)
                    .sized(0.7F, 2.4F)
                    .clientTrackingRange(10)
                    .build("the_mourner"));

    public static final RegistrySupplier<EntityType<lykrast.defiledlands.common.entity.passive.BookWyrmEntity>> BOOK_WYRM = ENTITIES.register("book_wyrm", () ->
            EntityType.Builder.of(lykrast.defiledlands.common.entity.passive.BookWyrmEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F)
                    .clientTrackingRange(10)
                    .build("book_wyrm"));

    public static final RegistrySupplier<EntityType<BlastemFruitEntity>> BLASTEM_FRUIT = ENTITIES.register("blastem_fruit", () ->
            EntityType.Builder.<BlastemFruitEntity>of(BlastemFruitEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("blastem_fruit"));

    public static final RegistrySupplier<EntityType<BlastemFruitBlazingEntity>> BLASTEM_FRUIT_BLAZING = ENTITIES.register("blastem_fruit_blazing", () ->
            EntityType.Builder.<BlastemFruitBlazingEntity>of(BlastemFruitBlazingEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("blastem_fruit_blazing"));

    public static void register() {
        ENTITIES.register();
    }

    public static void registerAttributes() {
        EntityAttributeRegistry.register(SHAMBLER, ShamblerEntity::createAttributes);
        EntityAttributeRegistry.register(SHAMBLER_TWISTED, TwistedShamblerEntity::createAttributes);
        EntityAttributeRegistry.register(SCUTTLER, ScuttlerEntity::createAttributes);
        EntityAttributeRegistry.register(HOST, HostEntity::createAttributes);
        EntityAttributeRegistry.register(SLIME_DEFILED, DefiledSlimeEntity::createAttributes);
        EntityAttributeRegistry.register(DESTROYER, lykrast.defiledlands.common.entity.boss.DestroyerEntity::createAttributes);
        EntityAttributeRegistry.register(MOURNER, lykrast.defiledlands.common.entity.boss.MournerEntity::createAttributes);
        EntityAttributeRegistry.register(BOOK_WYRM, lykrast.defiledlands.common.entity.passive.BookWyrmEntity::createAttributes);
    }
}
