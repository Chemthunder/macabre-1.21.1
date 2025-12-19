package silly.chemthunder.macabre.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import silly.chemthunder.macabre.index.MacabreDataComponents;

public class GauntletItem extends Item {
    public GauntletItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        var component = MacabreDataComponents.GAUNTLET_COOLDOWN;

        if (!(stack.getOrDefault(component, 0) == 5)) {
            stack.set(component, stack.getOrDefault(component, 0) + 1);
        }
        return super.postHit(stack, target, attacker);
    }

//    @Override
//    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
//        if (stack.getOrDefault(MacabreDataComponents.GAUNTLET_COOLDOWN, 0) > 0) {
//            stack.set(MacabreDataComponents.GAUNTLET_COOLDOWN, stack.getOrDefault(MacabreDataComponents.GAUNTLET_COOLDOWN, 0) - 1);
//        }
//        super.inventoryTick(stack, world, entity, slot, selected);
//    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(MacabreDataComponents.GAUNTLET_COOLDOWN, 0) / 5 * 13);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getOrDefault(MacabreDataComponents.GAUNTLET_COOLDOWN, 0) > 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xa8325a;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var component = MacabreDataComponents.GAUNTLET_COOLDOWN;
        ItemStack stack = user.getStackInHand(hand);

        if (stack.getOrDefault(component, 0) == 5) {
            stack.set(component, 0);

            user.setVelocity(0, 2, 0);
            user.velocityModified = true;
        }

        return super.use(world, user, hand);
    }
}
