package tfar.fastfurnace;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;

public interface AbstractFurnaceBlockEntityDuck {
	AbstractCookingRecipe getCachedRecipe();
	void setRecipe(AbstractCookingRecipe recipe);
	ItemStack getFailedMatch();
	void setFailedMatch(ItemStack stack);
}
