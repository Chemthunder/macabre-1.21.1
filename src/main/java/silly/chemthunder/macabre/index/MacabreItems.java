package silly.chemthunder.macabre.index;

import net.acoyt.acornlib.api.item.AcornItemSettings;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import silly.chemthunder.macabre.Macabre;
import silly.chemthunder.macabre.item.BoneSawItem;
import silly.chemthunder.macabre.item.GauntletItem;

import java.util.function.Function;

public interface MacabreItems {
    Item GAUNTLET = create("gauntlet", GauntletItem::new, new Item.Settings()
            .maxCount(1)
    );

    Item BONE_SAW = create("bone_saw", BoneSawItem::new, new AcornItemSettings()
            .maxCount(1)
            .attributeModifiers(BoneSawItem.createAttributeModifiers())
    );

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Macabre.id(name), item);
    }

    static void index() {}
}
