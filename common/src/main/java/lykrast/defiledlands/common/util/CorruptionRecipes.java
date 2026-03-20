package lykrast.defiledlands.common.util;

import lykrast.defiledlands.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * 污化配方系统
 * 定义方块之间的污化转换关系
 */
public class CorruptionRecipes {

    private static final Map<BlockState, BlockState> CORRUPTION_MAP = new HashMap<>();

    /**
     * 初始化所有污化配方
     */
    public static void init() {
        // ---- 基础地形 ----
        register(Blocks.STONE, ModBlocks.STONE_DEFILED.get());
        register(Blocks.COBBLESTONE, ModBlocks.STONE_DEFILED.get());
        register(Blocks.ANDESITE, ModBlocks.STONE_DEFILED.get());
        register(Blocks.DIORITE, ModBlocks.STONE_DEFILED.get());
        register(Blocks.GRANITE, ModBlocks.STONE_DEFILED.get());
        register(Blocks.DIRT, ModBlocks.DIRT_DEFILED.get());
        register(Blocks.GRASS_BLOCK, ModBlocks.GRASS_DEFILED.get());
        register(Blocks.SAND, ModBlocks.SAND_DEFILED.get());
        register(Blocks.SANDSTONE, ModBlocks.SANDSTONE_CORRUPTED.get());
        register(Blocks.GRAVEL, ModBlocks.GRAVEL_DEFILED.get());

        // ---- 玻璃（实心） ----
        register(Blocks.GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.WHITE_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.ORANGE_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.MAGENTA_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIGHT_BLUE_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.YELLOW_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIME_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.PINK_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.GRAY_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIGHT_GRAY_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.CYAN_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.PURPLE_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BLUE_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BROWN_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.GREEN_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.RED_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BLACK_STAINED_GLASS, ModBlocks.GLASS_OBSCURE.get());

        // ---- 玻璃板 ----
        register(Blocks.GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.WHITE_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.ORANGE_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.MAGENTA_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.YELLOW_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIME_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.PINK_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.GRAY_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.CYAN_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.PURPLE_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BLUE_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BROWN_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.GREEN_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.RED_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());
        register(Blocks.BLACK_STAINED_GLASS_PANE, ModBlocks.GLASS_OBSCURE.get());

        // ---- 石砖 ----
        register(Blocks.STONE_BRICKS, ModBlocks.STONE_BRICKS_DEFILED.get());
        register(Blocks.CRACKED_STONE_BRICKS, ModBlocks.CRACKED_STONE_BRICKS_DEFILED.get());
        register(Blocks.MOSSY_STONE_BRICKS, ModBlocks.MOSSY_STONE_BRICKS_DEFILED.get());
        register(Blocks.MOSSY_COBBLESTONE, ModBlocks.MOSSY_STONE_DEFILED.get());

        // ---- 矿石和矿物块 ----
        register(Blocks.COAL_ORE, ModBlocks.HEPHAESTITE_ORE.get());
        register(Blocks.DEEPSLATE_COAL_ORE, ModBlocks.HEPHAESTITE_ORE.get());
        register(Blocks.COAL_BLOCK, ModBlocks.HEPHAESTITE_BLOCK.get());

        register(Blocks.IRON_ORE, ModBlocks.UMBRIUM_ORE.get());
        register(Blocks.DEEPSLATE_IRON_ORE, ModBlocks.UMBRIUM_ORE.get());
        register(Blocks.IRON_BLOCK, ModBlocks.UMBRIUM_BLOCK.get());

        register(Blocks.DIAMOND_ORE, ModBlocks.SCARLITE_ORE.get());
        register(Blocks.DEEPSLATE_DIAMOND_ORE, ModBlocks.SCARLITE_ORE.get());
        register(Blocks.DIAMOND_BLOCK, ModBlocks.SCARLITE_BLOCK.get());
    }

    /**
     * 获取方块状态的污化结果
     */
    public static BlockState getCorrupted(BlockState input) {
        return CORRUPTION_MAP.get(input);
    }

    /**
     * 检查方块状态是否可以被污化
     */
    public static boolean isCorruptable(BlockState input) {
        return CORRUPTION_MAP.containsKey(input);
    }

    /**
     * 注册污化配方（方块状态 -> 方块状态）
     */
    public static void register(BlockState input, BlockState output) {
        CORRUPTION_MAP.put(input, output);
    }

    /**
     * 注册污化配方（方块 -> 方块）
     */
    public static void register(Block input, Block output) {
        CORRUPTION_MAP.put(input.defaultBlockState(), output.defaultBlockState());
    }

    /**
     * 注册污化配方（方块状态 -> 方块）
     */
    public static void register(BlockState input, Block output) {
        CORRUPTION_MAP.put(input, output.defaultBlockState());
    }

    /**
     * 注册污化配方（方块 -> 方块状态）
     */
    public static void register(Block input, BlockState output) {
        CORRUPTION_MAP.put(input.defaultBlockState(), output);
    }

    /**
     * 取消注册污化配方
     */
    public static void unregister(BlockState input) {
        CORRUPTION_MAP.remove(input);
    }

    /**
     * 获取完整的污化映射表（用于 JEI 显示）
     */
    public static Map<BlockState, BlockState> getMap() {
        return CORRUPTION_MAP;
    }
}
