package tfar.fastfurnace.mixin;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.fastfurnace.AbstractFurnaceBlockEntityDuck;
import tfar.fastfurnace.Hooks;

import java.util.Map;
import java.util.Optional;

@Mixin(AbstractFurnaceBlockEntity.class)
class AbstractFurnaceBlockEntityMixin implements AbstractFurnaceBlockEntityDuck {

    @Shadow
    int litTime;
    protected AbstractCookingRecipe cachedRecipe = null;
    protected ItemStack failedMatch = ItemStack.EMPTY;

    @Inject(at = @At("HEAD"), method = "getFuel", cancellable = true)
    private static void rebuildFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> ci) {
        if (Hooks.rebuild) return;
        ci.setReturnValue(Hooks.fuelTimeMap);
    }

    @Redirect(method = "serverTick", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/Container;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private static Optional<? extends AbstractCookingRecipe> getCachedRecipe(RecipeManager recipeManager, RecipeType<? extends AbstractCookingRecipe> type, Container inventory, Level world) {
        return Hooks.lookUpRecipe((AbstractFurnaceBlockEntity) inventory, recipeManager, type);
    }

    /**
     * @author tfar
     * @reason furnace is bad
     */
    @Overwrite
    private static int getTotalCookTime(Level level, RecipeType<? extends AbstractCookingRecipe> recipeType, Container container) {
        AbstractFurnaceBlockEntityDuck furnace = (AbstractFurnaceBlockEntityDuck) container;
        return furnace.getCachedRecipe() != null ? furnace.getCachedRecipe().getCookingTime() : 200;
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void saveBurntime(CompoundTag compoundTag, CallbackInfo ci) {
       compoundTag.putInt(Hooks.FABRIC_BURN_TIME, this.litTime);
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void loadBurntime(CompoundTag tag, CallbackInfo ci) {
        this.litTime = tag.getInt(Hooks.FABRIC_BURN_TIME);
    }

    @Override
    public AbstractCookingRecipe getCachedRecipe() {
        return cachedRecipe;
    }

    @Override
    public void setRecipe(AbstractCookingRecipe recipe) {
        this.cachedRecipe = recipe;
    }

    @Override
    public ItemStack getFailedMatch() {
        return failedMatch;
    }

    @Override
    public void setFailedMatch(ItemStack stack) {
        this.failedMatch = stack;
    }

}
