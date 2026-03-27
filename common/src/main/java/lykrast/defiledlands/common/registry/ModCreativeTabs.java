package lykrast.defiledlands.common.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(DefiledLands.MOD_ID,
            Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> DEFILED_LANDS_TAB = TABS.register("defiled_lands",
            () -> CreativeTabRegistry.create(Component.translatable("itemGroup.defiledlands"),
                    () -> new ItemStack(ModItems.SCARLITE.get())));

    public static void register() {
        TABS.register();
        CreativeTabRegistry.append(DEFILED_LANDS_TAB,

                ModItems.DEFILEMENT_POWDER,
                ModItems.SCARLITE,
                ModItems.HEPHAESTITE,
                ModItems.BLACK_HEART,
                ModItems.SCUTTLER_EYE,
                ModItems.FOUL_CANDY,
                ModItems.UMBRIUM_INGOT,
                ModItems.UMBRIUM_NUGGET,
                ModItems.RAVAGING_INGOT,
                ModItems.BOOK_WYRM_SCALE,
                ModItems.BOOK_WYRM_SCALE_GOLDEN,
                ModItems.ESSENCE_MOURNER,
                ModItems.REMORSEFUL_GEM,
                ModItems.ESSENCE_DESTROYER,
                ModItems.FOUL_SLIME,
                ModItems.CALLING_STONE,
                ModItems.IDOL_SORROW,
                ModItems.BLASTEM_SEED,
                ModItems.BOOK_WYRM_RAW,
                ModItems.BOOK_WYRM_COOKED,

                ModItems.UMBRIUM_SWORD,
                ModItems.SCARLITE_REAVER,
                ModItems.SCARLITE_RAZOR,
                ModItems.BLASTEM_FRUIT,
                ModItems.BLASTEM_FRUIT_BLAZING,
                ModItems.UMBRIUM_HELMET,
                ModItems.UMBRIUM_CHESTPLATE,
                ModItems.UMBRIUM_LEGGINGS,
                ModItems.UMBRIUM_BOOTS,
                ModItems.SCALE_HELMET,
                ModItems.SCALE_CHESTPLATE,
                ModItems.SCALE_LEGGINGS,
                ModItems.SCALE_BOOTS,
                ModItems.SCALE_GOLDEN_HELMET,
                ModItems.SCALE_GOLDEN_CHESTPLATE,
                ModItems.SCALE_GOLDEN_LEGGINGS,
                ModItems.SCALE_GOLDEN_BOOTS,

                ModItems.UMBRIUM_AXE,
                ModItems.UMBRIUM_PICKAXE,
                ModItems.UMBRIUM_SHOVEL,
                ModItems.UMBRIUM_HOE,
                ModItems.RAVAGING_AXE,
                ModItems.RAVAGING_PICKAXE,
                ModItems.RAVAGING_SHOVEL,

                ModItems.BOOK_WYRM_ANALYZER,
                ModItems.SCARLITE_RING,
                ModItems.PHYTOPROSTASIA_AMULET,

                ModItems.GRASS_DEFILED,
                ModItems.DIRT_DEFILED,
                ModItems.GRAVEL_DEFILED,
                ModItems.SAND_DEFILED,
                ModItems.SANDSTONE_CORRUPTED,
                ModItems.STONE_CORRUPTED,
                ModItems.STONE_DEFILED,
                ModItems.STONE_BRICKS_DEFILED,
                ModItems.MOSSY_STONE_DEFILED,
                ModItems.MOSSY_STONE_BRICKS_DEFILED,
                ModItems.CRACKED_STONE_BRICKS_DEFILED,
                ModItems.RAVAGING_STONE,
                ModItems.RAVAGING_BRICKS,
                ModItems.HEPHAESTITE_ORE,
                ModItems.UMBRIUM_ORE,
                ModItems.SCARLITE_ORE,
                ModItems.HEPHAESTITE_BLOCK,
                ModItems.SCARLITE_BLOCK,
                ModItems.UMBRIUM_BLOCK,
                ModItems.HEALING_PAD,
                ModItems.CONJURING_ALTAR,
                ModItems.GLASS_OBSCURE,

                ModItems.TENEBRA_LOG,
                ModItems.TENEBRA_PLANKS,
                ModItems.TENEBRA_LEAVES,
                ModItems.TENEBRA_SAPLING,
                ModItems.SCURONOTTE,
                ModItems.BLASTEM,
                ModItems.VILESPINE,

                ModItems.TENEBRA_DOOR,
                ModItems.STONE_DEFILED_STAIRS,
                ModItems.SANDSTONE_DEFILED_STAIRS,
                ModItems.STONE_BRICKS_DEFILED_STAIRS,
                ModItems.TENEBRA_STAIRS,
                ModItems.STONE_DEFILED_SLAB,
                ModItems.SANDSTONE_DEFILED_SLAB,
                ModItems.STONE_BRICKS_DEFILED_SLAB,
                ModItems.TENEBRA_SLAB,

                ModItems.SHAMBLER_SPAWN_EGG,
                ModItems.SHAMBLER_TWISTED_SPAWN_EGG,
                ModItems.SCUTTLER_SPAWN_EGG,
                ModItems.HOST_SPAWN_EGG,
                ModItems.SLIME_DEFILED_SPAWN_EGG,
                ModItems.DESTROYER_SPAWN_EGG,
                ModItems.MOURNER_SPAWN_EGG,
                ModItems.BOOK_WYRM_SPAWN_EGG);
    }
}
