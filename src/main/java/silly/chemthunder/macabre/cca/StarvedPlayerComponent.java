package silly.chemthunder.macabre.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.macabre.Macabre;

public class StarvedPlayerComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<StarvedPlayerComponent> KEY = ComponentRegistry.getOrCreate(Macabre.id("starved"), StarvedPlayerComponent.class);
    private final PlayerEntity player;
    public int intervenedTicks = 0;

    public StarvedPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void tick() {
        if (intervenedTicks > 0) {
            intervenedTicks--;
            if (intervenedTicks == 0) {
                sync();
            }
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.intervenedTicks = nbtCompound.getInt("intervenedTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("intervenedTicks", 0);
    }
}
