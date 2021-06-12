package tfar.fastfurnace;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Hooks {

	public static Map<Item, Integer> fuelTimeMap = new HashMap<>();
	public static boolean rebuild = true;

	public static void rebuildFuelMap() {
		rebuild = true;
		fuelTimeMap = AbstractFurnaceBlockEntity.getFuel();
		rebuild = false;
	}

	public static Optional<? extends AbstractCookingRecipe> lookUpRecipe(AbstractFurnaceBlockEntity furnace, RecipeManager recipeManager, RecipeType<? extends AbstractCookingRecipe> recipeType) {
		ItemStack input = furnace.getItem(0);
		if (input.isEmpty() || input == ((AbstractFurnaceBlockEntityDuck) furnace).getFailedMatch())
			return Optional.empty();

		if (curRecipe(furnace) != null && curRecipe(furnace).matches(furnace, furnace.getLevel()))
			return Optional.of(curRecipe(furnace));
		else {
			AbstractCookingRecipe rec = recipeManager.getRecipeFor(recipeType, furnace, furnace.getLevel()).orElse(null);
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
		return ((AbstractFurnaceBlockEntityDuck) abstractFurnaceBlockEntity).getCachedRecipe();
	}

	public static void setCurRecipe(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, AbstractCookingRecipe recipe) {
		((AbstractFurnaceBlockEntityDuck) abstractFurnaceBlockEntity).setRecipe(recipe);
	}
}
