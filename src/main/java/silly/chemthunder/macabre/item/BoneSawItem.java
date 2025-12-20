package silly.chemthunder.macabre.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.ItemWithSkins;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import silly.chemthunder.macabre.api.item.CustomEnchantingMaterialItem;
import silly.chemthunder.macabre.cca.DashingComponent;
import silly.chemthunder.macabre.cca.StarvedPlayerComponent;
import silly.chemthunder.macabre.entity.BloodShotEntity;
import silly.chemthunder.macabre.index.*;

public class BoneSawItem extends Item implements ColorableItem, CustomKillSourceItem, CustomEnchantingMaterialItem {
    public BoneSawItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var component = MacabreDataComponents.SAW_CHARGE;
        ItemStack stack = user.getStackInHand(hand);
        boolean hasVanity = EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.VANITY);
        boolean hasDesecration = EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.DESECRATION);

        if (hasVanity) {
            if (stack.getOrDefault(component, 0) > 0) {
                spew(user);
            }
        } else {
            if (stack.getOrDefault(component, 0) == 5) {
                if (!hasDesecration) {
                    dash(user);
                }
            }
        }

        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean hasDesecration = EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.DESECRATION);
        var component = MacabreDataComponents.SAW_CHARGE;

        if (hasDesecration) {
            StarvedPlayerComponent starvedPlayerComponent = StarvedPlayerComponent.KEY.get(attacker);
            starvedPlayerComponent.intervenedTicks = 280;
            starvedPlayerComponent.sync();
        }
        return super.postHit(stack, target, attacker);
    }


    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.0F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.6F, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public void dash(PlayerEntity user) {
        var component = MacabreDataComponents.SAW_CHARGE;
        ItemStack stack = user.getStackInHand(user.getActiveHand());
        DashingComponent dash = DashingComponent.KEY.get(user);

        dash.dashingTicks = 10;
        dash.sync();

        float g = user.getYaw();
        float h = user.getPitch();
        float j = -MathHelper.sin(g * ((float)Math.PI / 180F)) * MathHelper.cos(h * ((float)Math.PI / 180F));
        float k = -MathHelper.sin(h * ((float)Math.PI / 180F));
        float l = MathHelper.cos(g * ((float)Math.PI / 180F)) * MathHelper.cos(h * ((float)Math.PI / 180F));
        float m = 2.5F;
        user.addVelocity(j*m,k*m, l*m);
        stack.set(component, 0);
        user.swingHand(user.getActiveHand());
    }

    public void spew(PlayerEntity user) {
        var component = MacabreDataComponents.SAW_CHARGE;
        ItemStack stack = user.getStackInHand(user.getActiveHand());
        World world = user.getWorld();

        BloodShotEntity shot = new BloodShotEntity(MacabreEntities.BLOOD_SHOT, world);
        shot.updatePosition(user.getX(), user.getY() + 1f, user.getZ());
        shot.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 7.5F * 0.5F, 0.0F);
        shot.velocityModified = true;
        stack.set(component, stack.getOrDefault(component, 0) - 1);

        if (!user.isInCreativeMode())  user.getItemCooldownManager().set(stack.getItem(), 10);

        user.damage(user.getDamageSources().generic(), 2);

        user.swingHand(Hand.MAIN_HAND);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnEntity(shot);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof PlayerEntity player){
            if (DashingComponent.KEY.get(player).dashingTicks > 0){
                for (LivingEntity livingEntity : world.getNonSpectatingEntities(LivingEntity.class, entity.getBoundingBox().expand(1.0F, 1.0F, 1.0F))) {
                    if (livingEntity != entity && !entity.isTeammate(livingEntity) && (!(livingEntity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity).isMarker()) && entity.squaredDistanceTo(livingEntity) < (double)9.0F) {
                        livingEntity.damage(livingEntity.getDamageSources().generic(), 15);
                    }
                }
            }

            if (player.isInCreativeMode()) {
                stack.set(MacabreDataComponents.SAW_CHARGE, 5);
            }
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 5;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isInCreativeMode();
    }

    // item bar
    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) stack.getOrDefault(MacabreDataComponents.SAW_CHARGE, 0) / 5 * 13);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.VANITY)) {
            return 0xcf1958;
        }
        if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.DESECRATION)) {
            return 0xffffff;
        }
        return 0xffdc7a;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MacabreEnchantments.DESECRATION)) {
            return false;
        }
        return true;
    }

    // tooltip
    @Override
    public int startColor(ItemStack itemStack) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.VANITY)) {
            return 0xFFff4267;
        }
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.DESECRATION)) {
            return 0xFFffffff;
        }
        return 0xFFe0a743;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.VANITY)) {
            return 0xFF5c0012;
        }
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.DESECRATION)) {
            return 0xFFa1a1a1;
        }
        return 0xFF754b00;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.VANITY)) {
            return 0xF01f060b;
        }
        if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, MacabreEnchantments.DESECRATION)) {
            return 0xF0292929;
        }
        return 0xF0362609;
    }

    @Override
    public DamageSource getKillSource(LivingEntity livingEntity) {
        return MacabreDamageTypes.arrogance(livingEntity);
    }

    @Override
    public Ingredient getMaterial(ItemStack stack) {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }
}
