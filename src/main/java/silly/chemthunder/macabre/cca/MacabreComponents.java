package silly.chemthunder.macabre.cca;

import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MacabreComponents implements EntityComponentInitializer {
    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, DashingComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(DashingComponent::new);
        entityComponentFactoryRegistry.beginRegistration(PlayerEntity.class, StarvedPlayerComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(StarvedPlayerComponent::new);
    }
}
