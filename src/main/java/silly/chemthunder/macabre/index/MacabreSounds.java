package silly.chemthunder.macabre.index;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import silly.chemthunder.macabre.Macabre;

public interface MacabreSounds {
    SoundEvent DIVINE_BELL = create("item.divine_bell");

    private static SoundEvent create(String name) {
        Identifier id = Macabre.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    static void index() {
        // Sound Events are Registered Statically
    }
}
