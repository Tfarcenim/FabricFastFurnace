package tfar.fastfurnace.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;
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
    private int burnTime;
    protected AbstractCookingRecipe cachedRecipe = null;
    protected ItemStack failedMatch = ItemStack.EMPTY;

    @Inject(at = @At("HEAD"), method = "createFuelTimeMap", cancellable = true)
    private static void rebuildFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> ci) {
        if (Hooks.rebuild) return;
        ci.setReturnValue(Hooks.fuelTimeMap);
    }

    @Redirect(method = "tick", at = @At(value = "INVOKE",
            target = "net/minecraft/recipe/RecipeManager.getFirstMatch(Lnet/minecraft/recipe/RecipeType;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/world/World;)Ljava/util/Optional;"))
    private Optional<? extends AbstractCookingRecipe> getCachedRecipe(RecipeManager recipeManager, RecipeType<? extends AbstractCookingRecipe> type, Inventory inventory, World world) {
        return Hooks.lookUpRecipe((AbstractFurnaceBlockEntity) (Object) this, recipeManager, type);
    }

    @Overwrite
    public int getCookTime() {
        return cachedRecipe != null ? cachedRecipe.getCookTime() : 200;
    }

    @Inject(method = "toTag", at = @At("RETURN"))
    private void saveBurntime(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        cir.getReturnValue().putInt("fabricBurnTime", this.burnTime);
    }

    @Inject(method = "fromTag", at = @At("RETURN"))
    private void loadBurntime(BlockState state, CompoundTag tag, CallbackInfo ci) {
        this.burnTime = tag.getInt("fabricBurnTime");
    }

    @Override
    public AbstractCookingRecipe getRecipe() {
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
