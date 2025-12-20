package silly.chemthunder.macabre.index;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import silly.chemthunder.macabre.Macabre;

import java.util.LinkedHashMap;
import java.util.Map;

public interface MacabreDataComponents {
    Map<ComponentType<?>, Identifier> COMPONENTS = new LinkedHashMap<>();

    ComponentType<Integer> GAUNTLET_COOLDOWN = create("gauntlet_cooldown", new ComponentType.Builder<Integer>()
            .codec(Codec.INT)
            .build()
    );

    ComponentType<Integer> SAW_CHARGE = create("saw_charge", new ComponentType.Builder<Integer>()
            .codec(Codec.INT)
            .build()
    );

    static <T extends ComponentType<?>> T create(String name, T component) {
        COMPONENTS.put(component, Macabre.id(name));
        return component;
    }

    static void index() {
        COMPONENTS.keySet().forEach(component -> {
            Registry.register(Registries.DATA_COMPONENT_TYPE, COMPONENTS.get(component), component);
        });
    }
}
