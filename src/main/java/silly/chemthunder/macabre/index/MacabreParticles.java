package silly.chemthunder.macabre.index;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import silly.chemthunder.macabre.Macabre;

public interface MacabreParticles {
    SimpleParticleType BLOOD = FabricParticleTypes.simple(true);

    private static void create(String name, ParticleType<?> particle) {
        Registry.register(Registries.PARTICLE_TYPE, Macabre.id(name), particle);
    }

    static void index() {
        create("blood", BLOOD);
    }

    static void clientIndex() {
        ParticleFactoryRegistry.getInstance().register(BLOOD, FlameParticle.Factory::new);
    }
}