package silly.chemthunder.macabre;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silly.chemthunder.macabre.index.MacabreDataComponents;
import silly.chemthunder.macabre.index.MacabreEntities;
import silly.chemthunder.macabre.index.MacabreItemGroups;
import silly.chemthunder.macabre.index.MacabreItems;

public class Macabre implements ModInitializer {
	public static final String MOD_ID = "macabre";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        MacabreItems.index();
        MacabreDataComponents.index();
        MacabreItemGroups.index();
        MacabreEntities.index();

		LOGGER.info("Boingle Doingle");
	}
}