package lykrast.defiledlands.compat.jei;

import net.minecraft.world.level.block.state.BlockState;

/**
 * 污化配方数据类，用于 JEI 显示
 */
public class CorruptionRecipe {
    private final BlockState input;
    private final BlockState output;

    public CorruptionRecipe(BlockState input, BlockState output) {
        this.input = input;
        this.output = output;
    }

    public BlockState getInput() {
        return input;
    }

    public BlockState getOutput() {
        return output;
    }
}
