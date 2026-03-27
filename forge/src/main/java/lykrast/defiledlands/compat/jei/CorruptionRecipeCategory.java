package lykrast.defiledlands.compat.jei;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.registry.ModItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CorruptionRecipeCategory implements IRecipeCategory<CorruptionRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(DefiledLands.MOD_ID, "corruption");
    public static final RecipeType<CorruptionRecipe> RECIPE_TYPE = new RecipeType<>(UID, CorruptionRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public CorruptionRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(116, 54);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(ModItems.DEFILEMENT_POWDER.get()));
    }

    @Override
    public RecipeType<CorruptionRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.defiledlands.category.corruption");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CorruptionRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 19)
                .addItemStack(new ItemStack(recipe.getInput().getBlock()));

        builder.addSlot(RecipeIngredientRole.CATALYST, 49, 19)
                .addItemStack(new ItemStack(ModItems.DEFILEMENT_POWDER.get()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 19)
                .addItemStack(new ItemStack(recipe.getOutput().getBlock()));
    }
}
