package lykrast.defiledlands.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import lykrast.defiledlands.common.DefiledLands;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.item.ItemPhytoprostasiaAmulet;
import lykrast.defiledlands.common.item.ItemScarliteRing;
import lykrast.defiledlands.common.item.ModArmorMaterials;
import lykrast.defiledlands.common.item.ModTiers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(DefiledLands.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> DEFILEMENT_POWDER = ITEMS.register("defilement_powder", () -> new lykrast.defiledlands.common.item.DefilementPowderItem(new Item.Properties()));
    public static final RegistrySupplier<Item> SCARLITE = ITEMS.register("scarlite", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> HEPHAESTITE = ITEMS.register("hephaestite", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> BLACK_HEART = ITEMS.register("black_heart", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> SCUTTLER_EYE = ITEMS.register("scuttler_eye", () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> UMBRIUM_INGOT = ITEMS.register("umbrium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_NUGGET = ITEMS.register("umbrium_nugget", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> RAVAGING_INGOT = ITEMS.register("ravaging_ingot", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> BOOK_WYRM_SCALE = ITEMS.register("book_wyrm_scale", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> BOOK_WYRM_SCALE_GOLDEN = ITEMS.register("book_wyrm_scale_golden", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> ESSENCE_MOURNER = ITEMS.register("essence_mourner", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> REMORSEFUL_GEM = ITEMS.register("remorseful_gem", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> ESSENCE_DESTROYER = ITEMS.register("essence_destroyer", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> FOUL_SLIME = ITEMS.register("foul_slime", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> CALLING_STONE = ITEMS.register("calling_stone", () -> new lykrast.defiledlands.common.item.BossSummonerItem(ModEntityTypes.DESTROYER, new Item.Properties()));
    public static final RegistrySupplier<Item> IDOL_SORROW = ITEMS.register("idol_sorrow", () -> new lykrast.defiledlands.common.item.BossSummonerItem(ModEntityTypes.MOURNER, new Item.Properties()));
    public static final RegistrySupplier<Item> BLASTEM_SEED = ITEMS.register("blastem_seed", () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> BOOK_WYRM_ANALYZER = ITEMS.register("book_wyrm_analyzer", () -> new lykrast.defiledlands.common.item.BookWyrmAnalyzerItem(new Item.Properties()));

    public static final RegistrySupplier<Item> DESTROYER_SPAWN_EGG = ITEMS.register("destroyer_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.DESTROYER, 0x51007F, 0x190026, new Item.Properties()));
    public static final RegistrySupplier<Item> MOURNER_SPAWN_EGG = ITEMS.register("mourner_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.MOURNER, 0x51007F, 0xFF00DC, new Item.Properties()));
    public static final RegistrySupplier<Item> BOOK_WYRM_SPAWN_EGG = ITEMS.register("book_wyrm_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.BOOK_WYRM, 0x3d3042, 0x775c81, new Item.Properties()));

    public static final RegistrySupplier<Item> FOUL_CANDY = ITEMS.register("foul_candy", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).alwaysEat().build())));
    public static final RegistrySupplier<Item> BOOK_WYRM_RAW = ITEMS.register("book_wyrm_raw", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).meat().build())));
    public static final RegistrySupplier<Item> BOOK_WYRM_COOKED = ITEMS.register("book_wyrm_cooked", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).meat().build())));

    public static final RegistrySupplier<Item> BLASTEM_FRUIT = ITEMS.register("blastem_fruit", () -> new lykrast.defiledlands.common.item.BlastemFruitItem(new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> BLASTEM_FRUIT_BLAZING = ITEMS.register("blastem_fruit_blazing", () -> new lykrast.defiledlands.common.item.BlastemFruitBlazingItem(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> UMBRIUM_SWORD = ITEMS.register("umbrium_sword", () -> new SwordItem(ModTiers.UMBRIUM, 3, -2.4F, new Item.Properties()));
    public static final RegistrySupplier<Item> SCARLITE_REAVER = ITEMS.register("scarlite_reaver", () -> new lykrast.defiledlands.common.item.ScarliteReaverItem(ModTiers.SCARLITE, 6, -3.2F, new Item.Properties()));
    public static final RegistrySupplier<Item> SCARLITE_RAZOR = ITEMS.register("scarlite_razor", () -> new lykrast.defiledlands.common.item.ScarliteRazorItem(ModTiers.SCARLITE, 2, -1.2F, new Item.Properties()));

    public static final RegistrySupplier<Item> UMBRIUM_HELMET = ITEMS.register("umbrium_helmet", () -> new ArmorItem(ModArmorMaterials.UMBRIUM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_CHESTPLATE = ITEMS.register("umbrium_chestplate", () -> new ArmorItem(ModArmorMaterials.UMBRIUM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_LEGGINGS = ITEMS.register("umbrium_leggings", () -> new ArmorItem(ModArmorMaterials.UMBRIUM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_BOOTS = ITEMS.register("umbrium_boots", () -> new ArmorItem(ModArmorMaterials.UMBRIUM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> UMBRA_HELMET = ITEMS.register("umbra_helmet", () -> new ArmorItem(ModArmorMaterials.UMBRA, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRA_CHESTPLATE = ITEMS.register("umbra_chestplate", () -> new ArmorItem(ModArmorMaterials.UMBRA, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRA_LEGGINGS = ITEMS.register("umbra_leggings", () -> new ArmorItem(ModArmorMaterials.UMBRA, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRA_BOOTS = ITEMS.register("umbra_boots", () -> new ArmorItem(ModArmorMaterials.UMBRA, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> SCALE_HELMET = ITEMS.register("scale_helmet", () -> new ArmorItem(ModArmorMaterials.BOOK_WYRM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_CHESTPLATE = ITEMS.register("scale_chestplate", () -> new ArmorItem(ModArmorMaterials.BOOK_WYRM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_LEGGINGS = ITEMS.register("scale_leggings", () -> new ArmorItem(ModArmorMaterials.BOOK_WYRM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_BOOTS = ITEMS.register("scale_boots", () -> new ArmorItem(ModArmorMaterials.BOOK_WYRM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> SCALE_GOLDEN_HELMET = ITEMS.register("scale_golden_helmet", () -> new ArmorItem(ModArmorMaterials.GOLDEN_BOOK_WYRM, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_GOLDEN_CHESTPLATE = ITEMS.register("scale_golden_chestplate", () -> new ArmorItem(ModArmorMaterials.GOLDEN_BOOK_WYRM, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_GOLDEN_LEGGINGS = ITEMS.register("scale_golden_leggings", () -> new ArmorItem(ModArmorMaterials.GOLDEN_BOOK_WYRM, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> SCALE_GOLDEN_BOOTS = ITEMS.register("scale_golden_boots", () -> new ArmorItem(ModArmorMaterials.GOLDEN_BOOK_WYRM, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> UMBRIUM_AXE = ITEMS.register("umbrium_axe", () -> new AxeItem(ModTiers.UMBRIUM, 6.0F, -3.1F, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_PICKAXE = ITEMS.register("umbrium_pickaxe", () -> new PickaxeItem(ModTiers.UMBRIUM, 1, -2.8F, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_SHOVEL = ITEMS.register("umbrium_shovel", () -> new ShovelItem(ModTiers.UMBRIUM, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_HOE = ITEMS.register("umbrium_hoe", () -> new HoeItem(ModTiers.UMBRIUM, -2, -1.0F, new Item.Properties()));

    public static final RegistrySupplier<Item> RAVAGING_AXE = ITEMS.register("ravaging_axe", () -> new AxeItem(ModTiers.RAVAGING, 6.0F, -3.1F, new Item.Properties()));
    public static final RegistrySupplier<Item> RAVAGING_PICKAXE = ITEMS.register("ravaging_pickaxe", () -> new PickaxeItem(ModTiers.RAVAGING, 1, -2.8F, new Item.Properties()));
    public static final RegistrySupplier<Item> RAVAGING_SHOVEL = ITEMS.register("ravaging_shovel", () -> new ShovelItem(ModTiers.RAVAGING, 1.5F, -3.0F, new Item.Properties()));

    public static final RegistrySupplier<Item> SCARLITE_RING = ITEMS.register("scarlite_ring", () -> new ItemScarliteRing(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> PHYTOPROSTASIA_AMULET = ITEMS.register("phytoprostasia_amulet", () -> new ItemPhytoprostasiaAmulet(new Item.Properties().stacksTo(1)));

    public static final RegistrySupplier<Item> GRASS_DEFILED = ITEMS.register("grass_defiled", () -> new BlockItem(ModBlocks.GRASS_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> GRAVEL_DEFILED = ITEMS.register("gravel_defiled", () -> new BlockItem(ModBlocks.GRAVEL_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SAND_DEFILED = ITEMS.register("sand_defiled", () -> new BlockItem(ModBlocks.SAND_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SANDSTONE_CORRUPTED = ITEMS.register("sandstone_defiled", () -> new BlockItem(ModBlocks.SANDSTONE_CORRUPTED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_CORRUPTED = ITEMS.register("stone_corrupted", () -> new BlockItem(ModBlocks.STONE_CORRUPTED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_DEFILED = ITEMS.register("stone_defiled", () -> new BlockItem(ModBlocks.STONE_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_BRICKS_DEFILED = ITEMS.register("defiled_stone_bricks", () -> new BlockItem(ModBlocks.STONE_BRICKS_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> MOSSY_STONE_DEFILED = ITEMS.register("mossy_defiled_stone", () -> new BlockItem(ModBlocks.MOSSY_STONE_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> MOSSY_STONE_BRICKS_DEFILED = ITEMS.register("mossy_defiled_stone_bricks", () -> new BlockItem(ModBlocks.MOSSY_STONE_BRICKS_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> CRACKED_STONE_BRICKS_DEFILED = ITEMS.register("cracked_defiled_stone_bricks", () -> new BlockItem(ModBlocks.CRACKED_STONE_BRICKS_DEFILED.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> RAVAGING_STONE = ITEMS.register("ravaging_stone", () -> new BlockItem(ModBlocks.RAVAGING_STONE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> RAVAGING_BRICKS = ITEMS.register("ravaging_bricks", () -> new BlockItem(ModBlocks.RAVAGING_BRICKS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> DIRT_DEFILED = ITEMS.register("defiled_dirt", () -> new BlockItem(ModBlocks.DIRT_DEFILED.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> HEPHAESTITE_ORE = ITEMS.register("hephaestite_ore", () -> new BlockItem(ModBlocks.HEPHAESTITE_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_ORE = ITEMS.register("umbrium_ore", () -> new BlockItem(ModBlocks.UMBRIUM_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SCARLITE_ORE = ITEMS.register("scarlite_ore", () -> new BlockItem(ModBlocks.SCARLITE_ORE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> HEPHAESTITE_BLOCK = ITEMS.register("hephaestite_block", () -> new BlockItem(ModBlocks.HEPHAESTITE_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SCARLITE_BLOCK = ITEMS.register("scarlite_block", () -> new BlockItem(ModBlocks.SCARLITE_BLOCK.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> UMBRIUM_BLOCK = ITEMS.register("umbrium_block", () -> new BlockItem(ModBlocks.UMBRIUM_BLOCK.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> TENEBRA_LOG = ITEMS.register("tenebra_log", () -> new BlockItem(ModBlocks.TENEBRA_LOG.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TENEBRA_PLANKS = ITEMS.register("tenebra_planks", () -> new BlockItem(ModBlocks.TENEBRA_PLANKS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TENEBRA_LEAVES = ITEMS.register("tenebra_leaves", () -> new BlockItem(ModBlocks.TENEBRA_LEAVES.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TENEBRA_SAPLING = ITEMS.register("tenebra_sapling", () -> new BlockItem(ModBlocks.TENEBRA_SAPLING.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> SCURONOTTE = ITEMS.register("scuronotte", () -> new BlockItem(ModBlocks.SCURONOTTE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> BLASTEM = ITEMS.register("blastem", () -> new BlockItem(ModBlocks.BLASTEM.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> VILESPINE = ITEMS.register("vilespine", () -> new BlockItem(ModBlocks.VILESPINE.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> HEALING_PAD = ITEMS.register("healing_pad", () -> new BlockItem(ModBlocks.HEALING_PAD.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> CONJURING_ALTAR = ITEMS.register("conjuring_altar", () -> new BlockItem(ModBlocks.CONJURING_ALTAR.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> GLASS_OBSCURE = ITEMS.register("glass_obscure", () -> new BlockItem(ModBlocks.GLASS_OBSCURE.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> TENEBRA_DOOR = ITEMS.register("tenebra_door", () -> new BlockItem(ModBlocks.TENEBRA_DOOR.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_DEFILED_STAIRS = ITEMS.register("stone_defiled_stairs", () -> new BlockItem(ModBlocks.STONE_DEFILED_STAIRS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SANDSTONE_DEFILED_STAIRS = ITEMS.register("sandstone_defiled_stairs", () -> new BlockItem(ModBlocks.SANDSTONE_DEFILED_STAIRS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_BRICKS_DEFILED_STAIRS = ITEMS.register("stone_defiled_bricks_stairs", () -> new BlockItem(ModBlocks.STONE_BRICKS_DEFILED_STAIRS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TENEBRA_STAIRS = ITEMS.register("tenebra_stairs", () -> new BlockItem(ModBlocks.TENEBRA_STAIRS.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_DEFILED_SLAB = ITEMS.register("stone_defiled_slab", () -> new BlockItem(ModBlocks.STONE_DEFILED_SLAB.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> SANDSTONE_DEFILED_SLAB = ITEMS.register("sandstone_defiled_slab", () -> new BlockItem(ModBlocks.SANDSTONE_DEFILED_SLAB.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> STONE_BRICKS_DEFILED_SLAB = ITEMS.register("stone_defiled_bricks_slab", () -> new BlockItem(ModBlocks.STONE_BRICKS_DEFILED_SLAB.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TENEBRA_SLAB = ITEMS.register("tenebra_slab", () -> new BlockItem(ModBlocks.TENEBRA_SLAB.get(), new Item.Properties()));

    public static final RegistrySupplier<Item> SHAMBLER_SPAWN_EGG = ITEMS.register("shambler_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.SHAMBLER, 0x171717, 0xebebeb, new Item.Properties()));
    public static final RegistrySupplier<Item> SHAMBLER_TWISTED_SPAWN_EGG = ITEMS.register("shambler_twisted_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.SHAMBLER_TWISTED, 0x171717, 0xf62e2e, new Item.Properties()));
    public static final RegistrySupplier<Item> SCUTTLER_SPAWN_EGG = ITEMS.register("scuttler_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.SCUTTLER, 0x211823, 0xce0bff, new Item.Properties()));
    public static final RegistrySupplier<Item> HOST_SPAWN_EGG = ITEMS.register("host_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.HOST, 0x3a3a3a, 0xc873a0, new Item.Properties()));
    public static final RegistrySupplier<Item> SLIME_DEFILED_SPAWN_EGG = ITEMS.register("slime_defiled_spawn_egg", () -> new dev.architectury.core.item.ArchitecturySpawnEggItem(ModEntityTypes.SLIME_DEFILED, 0xbe6d91, 0xc873a0, new Item.Properties()));

    public static void register() {
        ITEMS.register();
    }
}
