package tfar.fastfurnace.mixin;

import com.google.gson.JsonObject;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.fastfurnace.Hooks;

import java.util.Map;

@Mixin(RecipeManager.class)
class RecipeManagerMixin {
	@Inject(method = "apply",at = @At("RETURN"))
	private void rebuildFuelMap(Map<Identifier, JsonObject> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci){
		Hooks.rebuildFuelMap();
	}
}
