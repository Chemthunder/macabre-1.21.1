package silly.chemthunder.macabre.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import silly.chemthunder.macabre.Macabre;

public interface MacabreItemGroups {
    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Macabre.id("main"));
    ItemGroup A_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MacabreItems.GAUNTLET))
            .displayName(Text.translatable("itemgroup.macabre"))
            .build();

    static void index() {
        Registry.register(Registries.ITEM_GROUP, GROUP_KEY, A_GROUP);
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(MacabreItemGroups::addEntries);

    }

    private static void addEntries(FabricItemGroupEntries itemGroup) {
        itemGroup.add(MacabreItems.GAUNTLET);
    }
}
