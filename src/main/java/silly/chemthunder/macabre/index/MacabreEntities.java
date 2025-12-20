package silly.chemthunder.macabre.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import silly.chemthunder.macabre.Macabre;
import silly.chemthunder.macabre.entity.BloodShotEntity;

public interface MacabreEntities {
    EntityType<BloodShotEntity> BLOOD_SHOT = create(
            "blood_shot",
            EntityType.Builder.create(
                    BloodShotEntity::new,
                    SpawnGroup.MISC
            ).dimensions(1.6f, 1.6f)
    );

    static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Macabre.id(name));
        return Registry.register(Registries.ENTITY_TYPE, key.getValue(), builder.build(String.valueOf(key)));
    }

    static void index() {
//        // Entities are Registered Statically
     //   FabricDefaultAttributeRegistry.register(SHOCKWAVE, ShockwaveEntity.createAttribute());
    }

    static void clientIndex() {
        EntityRendererRegistry.register(BLOOD_SHOT, EmptyEntityRenderer::new);
    }
}
