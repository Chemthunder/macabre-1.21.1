package silly.chemthunder.macabre.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;

public interface CustomEnchantingMaterialItem {
    Ingredient getMaterial(ItemStack stack);
}
