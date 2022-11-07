package tfar.fastfurnace.mixin;

import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.fastfurnace.Hooks;

@Mixin(RecipeManager.class)
class ServerResourceManagerMixin {
	@Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V",at = @At("RETURN"))
	private void rebuildFuelMap(CallbackInfo ci){
		Hooks.rebuildFuelMap();
	}
}
