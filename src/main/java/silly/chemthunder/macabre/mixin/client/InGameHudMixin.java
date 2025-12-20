package silly.chemthunder.macabre.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import silly.chemthunder.macabre.Macabre;
import silly.chemthunder.macabre.cca.StarvedPlayerComponent;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Unique
    private static final Identifier NEW_EMPTY = Macabre.id("hud/new_empty.png");

    @Unique
    private static final Identifier NEW_HALF = Macabre.id("hud/new_half.png");

    @Unique
    private static final Identifier NEW_FULL = Macabre.id("hud/new_full.png");



    @WrapOperation(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
    private void setNewEmpty(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;
        if (StarvedPlayerComponent.KEY.get(player).intervenedTicks > 0) {
            original.call(instance, NEW_EMPTY, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, width, height);
        }
    }

    @WrapOperation(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
    private void setNewFull(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;
        if (StarvedPlayerComponent.KEY.get(player).intervenedTicks > 0) {
            original.call(instance, NEW_FULL, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, width, height);
        }
    }

    @WrapOperation(method = "renderFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 2))
    private void setNewHalf(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;
        if (StarvedPlayerComponent.KEY.get(player).intervenedTicks > 0) {
            original.call(instance, NEW_HALF, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, width, height);
        }
    }
}
