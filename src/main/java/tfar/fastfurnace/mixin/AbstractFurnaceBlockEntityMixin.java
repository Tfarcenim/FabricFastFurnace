package tfar.fastfurnace.mixin;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.fastfurnace.Hooks;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
class AbstractFurnaceBlockEntityMixin {

    @Shadow
    int litTime;

    @Inject(at = @At("HEAD"), method = "getFuel", cancellable = true)
    private static void rebuildFuelTimeMap(CallbackInfoReturnable<Map<Item, Integer>> ci) {
        if (Hooks.rebuild) return;
        ci.setReturnValue(Hooks.fuelTimeMap);
    }

    @Inject(method = "saveAdditional", at = @At("RETURN"))
    private void saveBurntime(CompoundTag compoundTag, CallbackInfo ci) {
       compoundTag.putInt(Hooks.FABRIC_BURN_TIME, this.litTime);
    }

    @Inject(method = "load", at = @At("RETURN"))
    private void loadBurntime(CompoundTag tag, CallbackInfo ci) {
        this.litTime = tag.getInt(Hooks.FABRIC_BURN_TIME);
    }

}
