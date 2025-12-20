package silly.chemthunder.macabre.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.macabre.Macabre;

public class DashingComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<DashingComponent> KEY = ComponentRegistry.getOrCreate(Macabre.id("dashing"), DashingComponent.class);
    private final PlayerEntity player;
    public int dashingTicks = 0;

    public DashingComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void tick() {
        if (dashingTicks > 0) {
            dashingTicks--;
            addParticles(player.getWorld(), player);
            if (dashingTicks == 0) {
                sync();
            }
        }
    }

    private void addParticles(World world, PlayerEntity player) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    ParticleTypes.END_ROD,
                    player.getX(),
                    player.getY() + 1.0f,
                    player.getZ(),
                    2,
                    0.2,
                    0.2,
                    0.2,
                    0.02
            );
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.dashingTicks = nbtCompound.getInt("dashingTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("dashingTicks", 0);
    }
}
