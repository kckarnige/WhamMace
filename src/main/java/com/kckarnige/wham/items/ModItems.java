package com.kckarnige.wham.items;

import com.kckarnige.wham.wham;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item MACE_HEAD = registerItem("mace_head", new Item.Settings().rarity(Rarity.EPIC));
    public static final Item MACE_TIP = registerItem("mace_tip", new Item.Settings().rarity(Rarity.EPIC));

    private static void addItemToItemGroup(FabricItemGroupEntries entries) {
        entries.add(MACE_HEAD);
        entries.add(MACE_TIP);
    }

    private static Item registerItem (String id, Item.Settings item) {
        // This is why plan ahead (don't ask why it took 2 commits to finish this thought)
        // Idk why tf I'm talking, I didn't plan shit 💀
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(wham.MOD_ID, id));
        return Registry.register(Registries.ITEM, key, new Item(item.registryKey(key)));
    }

    public static void registerModItems () {
        wham.LOGGER.info("[Wham!] Things feel different..");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToItemGroup);
    }
}
