package tfar.fastfurnace.mixin;

import net.minecraft.server.ServerResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.fastfurnace.Hooks;

@Mixin(ServerResources.class)
class ServerResourceManagerMixin {
	@Inject(method = "updateGlobals",at = @At("RETURN"))
	private void rebuildFuelMap(CallbackInfo ci){
		Hooks.rebuildFuelMap();
	}
}
