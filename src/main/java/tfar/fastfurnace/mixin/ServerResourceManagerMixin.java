package tfar.fastfurnace.mixin;

import net.minecraft.resource.ServerResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.fastfurnace.Hooks;

@Mixin(ServerResourceManager.class)
class ServerResourceManagerMixin {
	@Inject(method = "loadRegistryTags",at = @At("RETURN"))
	private void rebuildFuelMap(CallbackInfo ci){
		Hooks.rebuildFuelMap();
	}
}
