package silly.chemthunder.macabre;

import net.fabricmc.api.ClientModInitializer;
import silly.chemthunder.macabre.index.MacabreEntities;

public class MacabreClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MacabreEntities.clientIndex();
    }
}
