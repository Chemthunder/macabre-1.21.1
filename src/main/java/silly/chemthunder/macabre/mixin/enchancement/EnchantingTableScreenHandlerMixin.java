package silly.chemthunder.macabre.mixin.enchancement;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.enchancement.common.screenhandlers.EnchantingTableScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import silly.chemthunder.macabre.api.item.CustomEnchantingMaterialItem;

@Mixin(EnchantingTableScreenHandler.class)
public class EnchantingTableScreenHandlerMixin {
    @ModifyReturnValue(
            method = "getRepairIngredient(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/recipe/Ingredient;",
            at = @At("RETURN")
    )
    private Ingredient customMaterialSoNoTag(Ingredient original, ItemStack stack) {
        if (stack.getItem() instanceof CustomEnchantingMaterialItem item) {
            return item.getMaterial(stack);
        }

        return original;
    }
}