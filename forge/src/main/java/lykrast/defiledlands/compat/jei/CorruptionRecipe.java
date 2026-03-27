package lykrast.defiledlands.compat.jei;

import net.minecraft.world.level.block.state.BlockState;

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
