package lykrast.defiledlands.common.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Blocks;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(DefiledLands.MOD_ID, Registries.BLOCK);

    // ---- Terrain（污秽地形，使用 DefiledBlock / DefiledFallingBlock 基类实现扩散） ----
    public static final RegistrySupplier<Block> GRASS_DEFILED = BLOCKS.register("grass_defiled",
            () -> new GrassDefiledBlock(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).randomTicks()));
    public static final RegistrySupplier<Block> DIRT_DEFILED = BLOCKS.register("defiled_dirt",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> GRAVEL_DEFILED = BLOCKS.register("gravel_defiled",
            () -> new GravelDefiledBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL)));
    public static final RegistrySupplier<Block> SAND_DEFILED = BLOCKS.register("sand_defiled",
            () -> new DefiledFallingBlock(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistrySupplier<Block> SANDSTONE_CORRUPTED = BLOCKS.register("sandstone_defiled",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistrySupplier<Block> STONE_CORRUPTED = BLOCKS.register("stone_corrupted",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> STONE_DEFILED = BLOCKS.register("stone_defiled",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));

    // ---- Stone Bricks（装饰类，不扩散） ----
    public static final RegistrySupplier<Block> STONE_BRICKS_DEFILED = BLOCKS.register("defiled_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));
    public static final RegistrySupplier<Block> MOSSY_STONE_DEFILED = BLOCKS.register("mossy_defiled_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSSY_COBBLESTONE)));
    public static final RegistrySupplier<Block> MOSSY_STONE_BRICKS_DEFILED = BLOCKS.register("mossy_defiled_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.MOSSY_STONE_BRICKS)));
    public static final RegistrySupplier<Block> CRACKED_STONE_BRICKS_DEFILED = BLOCKS.register("cracked_defiled_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.CRACKED_STONE_BRICKS)));
    public static final RegistrySupplier<Block> RAVAGING_STONE = BLOCKS.register("ravaging_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistrySupplier<Block> RAVAGING_BRICKS = BLOCKS.register("ravaging_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE_BRICKS)));

    // ---- Ores & Metal Blocks（矿石使用 DefiledBlock 以支持扩散） ----
    public static final RegistrySupplier<Block> HEPHAESTITE_ORE = BLOCKS.register("hephaestite_ore",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistrySupplier<Block> UMBRIUM_ORE = BLOCKS.register("umbrium_ore",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistrySupplier<Block> SCARLITE_ORE = BLOCKS.register("scarlite_ore",
            () -> new DefiledBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistrySupplier<Block> HEPHAESTITE_BLOCK = BLOCKS.register("hephaestite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistrySupplier<Block> SCARLITE_BLOCK = BLOCKS.register("scarlite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistrySupplier<Block> UMBRIUM_BLOCK = BLOCKS.register("umbrium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    // ---- Plants & Woods ----
    public static final RegistrySupplier<Block> TENEBRA_LOG = BLOCKS.register("tenebra_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<Block> TENEBRA_PLANKS = BLOCKS.register("tenebra_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> TENEBRA_LEAVES = BLOCKS.register("tenebra_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> TENEBRA_SAPLING = BLOCKS.register("tenebra_sapling",
            () -> new TenebraSaplingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).randomTicks()));

    // ---- Herbs ----
    public static final RegistrySupplier<Block> SCURONOTTE = BLOCKS.register("scuronotte",
            () -> new DefiledPlantBlock(BlockBehaviour.Properties.copy(Blocks.DANDELION)));
    public static final RegistrySupplier<Block> BLASTEM = BLOCKS.register("blastem",
            () -> new BlastemBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).randomTicks()));
    public static final RegistrySupplier<Block> VILESPINE = BLOCKS.register("vilespine",
            () -> new VilespineBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).randomTicks()));

    // ---- Functional & Misc ----
    public static final RegistrySupplier<Block> HEALING_PAD = BLOCKS.register("healing_pad",
            () -> new HealingPadBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().lightLevel(s -> 4)));
    public static final RegistrySupplier<Block> CONJURING_ALTAR = BLOCKS.register("conjuring_altar",
            () -> new ConjuringAltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion().lightLevel(s -> 7)));
    public static final RegistrySupplier<Block> GLASS_OBSCURE = BLOCKS.register("glass_obscure",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));

    // ---- Building Blocks — Doors ----
    public static final RegistrySupplier<Block> TENEBRA_DOOR = BLOCKS.register("tenebra_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR),
                    net.minecraft.world.level.block.state.properties.BlockSetType.OAK));

    // ---- Building Blocks — Stairs ----
    public static final RegistrySupplier<Block> STONE_DEFILED_STAIRS = BLOCKS.register("stone_defiled_stairs",
            () -> new StairBlock(STONE_DEFILED.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_STAIRS)));
    public static final RegistrySupplier<Block> SANDSTONE_DEFILED_STAIRS = BLOCKS.register("sandstone_defiled_stairs",
            () -> new StairBlock(SANDSTONE_CORRUPTED.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.SANDSTONE_STAIRS)));
    public static final RegistrySupplier<Block> STONE_BRICKS_DEFILED_STAIRS = BLOCKS.register("stone_defiled_bricks_stairs",
            () -> new StairBlock(STONE_BRICKS_DEFILED.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_STAIRS)));
    public static final RegistrySupplier<Block> TENEBRA_STAIRS = BLOCKS.register("tenebra_stairs",
            () -> new StairBlock(TENEBRA_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));

    // ---- Building Blocks — Slabs ----
    public static final RegistrySupplier<Block> STONE_DEFILED_SLAB = BLOCKS.register("stone_defiled_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_SLAB)));
    public static final RegistrySupplier<Block> SANDSTONE_DEFILED_SLAB = BLOCKS.register("sandstone_defiled_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistrySupplier<Block> STONE_BRICKS_DEFILED_SLAB = BLOCKS.register("stone_defiled_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.STONE_BRICK_SLAB)));
    public static final RegistrySupplier<Block> TENEBRA_SLAB = BLOCKS.register("tenebra_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));

    public static void register() {
        BLOCKS.register();
    }
}
