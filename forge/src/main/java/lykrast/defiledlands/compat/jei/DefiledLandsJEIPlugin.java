package lykrast.defiledlands.compat.jei;

import lykrast.defiledlands.common.DefiledLands;
import lykrast.defiledlands.common.registry.ModItems;
import lykrast.defiledlands.common.util.CorruptionRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class DefiledLandsJEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = new ResourceLocation(DefiledLands.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new CorruptionRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        List<CorruptionRecipe> recipes = new ArrayList<>();
        CorruptionRecipes.getMap().forEach((input, output) ->
                recipes.add(new CorruptionRecipe(input, output))
        );
        registration.addRecipes(CorruptionRecipeCategory.RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

        registration.addRecipeCatalyst(
                new ItemStack(ModItems.DEFILEMENT_POWDER.get()),
                CorruptionRecipeCategory.RECIPE_TYPE
        );
    }
}
