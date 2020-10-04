package tfar.fastfurnace;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Hooks {

	public static Map<Item, Integer> fuelTimeMap = new HashMap<>();
	public static boolean rebuild = true;

	public static void rebuildFuelMap() {
		rebuild = true;
		fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
		rebuild = false;
	}

	public static Optional<? extends AbstractCookingRecipe> lookUpRecipe(AbstractFurnaceBlockEntity furnace, RecipeManager recipeManager, RecipeType<? extends AbstractCookingRecipe> recipeType) {
		ItemStack input = furnace.getStack(0);
		if (input.isEmpty() || input == ((AbstractFurnaceBlockEntityDuck) furnace).getFailedMatch())
			return Optional.empty();

		if (curRecipe(furnace) != null && curRecipe(furnace).matches(furnace, furnace.getWorld()))
			return Optional.of(curRecipe(furnace));
		else {
			AbstractCookingRecipe rec = recipeManager.getFirstMatch(recipeType, furnace, furnace.getWorld()).orElse(null);
			if (rec == null) setFailedMatch(furnace, input);
			else setFailedMatch(furnace, ItemStack.EMPTY);
			setCurRecipe(furnace, rec);
			return Optional.ofNullable(curRecipe(furnace));
		}
	}

	public static void setFailedMatch(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, ItemStack stack) {
		((AbstractFurnaceBlockEntityDuck) abstractFurnaceBlockEntity).setFailedMatch(stack);
	}

	public static AbstractCookingRecipe curRecipe(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
		return ((AbstractFurnaceBlockEntityDuck) abstractFurnaceBlockEntity).getRecipe();
	}

	public static void setCurRecipe(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, AbstractCookingRecipe recipe) {
		((AbstractFurnaceBlockEntityDuck) abstractFurnaceBlockEntity).setRecipe(recipe);
	}
}
