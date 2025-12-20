package silly.chemthunder.macabre.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import silly.chemthunder.macabre.index.MacabreDamageTypes;
import silly.chemthunder.macabre.index.MacabreItems;
import silly.chemthunder.macabre.index.MacabreParticles;

public class BloodShotEntity extends ThrownItemEntity implements Ownable {
    public int ageTicks = 0;

    public BloodShotEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected Item getDefaultItem() {
        return MacabreItems.BONE_SAW;
    }


    public void tick() {
        addParticles(this.getWorld());

        if (ageTicks > 0) {
            ageTicks++;
            if (ageTicks == 200) {
                this.discard();
            }
        }
        super.tick();
    }

    public void addParticles(World world) {

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    MacabreParticles.BLOOD,
                    this.getX(),
                    this.getY() + 0.5f,
                    this.getZ(),
                    3,
                    0.2,
                    0.2,
                    0.2,
                    0
            );
            serverWorld.spawnParticles(
                    MacabreParticles.BLOOD,
                    this.getX(),
                    this.getY() + 0.5f,
                    this.getZ(),
                    1,
                    0,
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        LivingEntity livingEntity = (LivingEntity) entityHitResult.getEntity();

        livingEntity.damage(MacabreDamageTypes.butchering(livingEntity), 5);
        this.discard();
        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if (!state.isIn(BlockTags.AIR)) {
            this.discard();
        }
        super.onBlockCollision(state);
    }
}
