package silly.chemthunder.macabre.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class ShockwaveEntity extends Entity {
    public int ageTicks = 0;
    public ShockwaveEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        if (ageTicks > 0) {
            ageTicks++;
            if (ageTicks == 30) {
                this.discard();
            }
        }

        super.tick();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        ageTicks = nbt.getInt("ageTicks");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("ageTicks", ageTicks);
    }

    public static DefaultAttributeContainer.Builder createAttribute() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1);
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
    public void pushAwayFrom(Entity entity) {
        super.pushAwayFrom(entity);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean canMoveVoluntarily() {
        return false;
    }
}
