package silly.chemthunder.macabre.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.chemthunder.macabre.cca.StarvedPlayerComponent;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void disableHunger(PlayerEntity player, CallbackInfo ci) {
        if (StarvedPlayerComponent.KEY.get(player).intervenedTicks > 0) {
            ci.cancel();
        }
    }
}
