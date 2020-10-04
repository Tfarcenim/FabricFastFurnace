package tfar.fastfurnace;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;

public interface AbstractFurnaceBlockEntityDuck {
	AbstractCookingRecipe getRecipe();
	void setRecipe(AbstractCookingRecipe recipe);
	ItemStack getFailedMatch();
	void setFailedMatch(ItemStack stack);
}
