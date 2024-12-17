package com.kckarnige.wham.items;

import com.kckarnige.wham.wham;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item MACE_HEAD = registerItem("mace_head", new Item(new Item.Settings().rarity(Rarity.EPIC)));

    private static void addItemToItemGroup(FabricItemGroupEntries entries) {
        entries.add(MACE_HEAD);
    }

    private static Item registerItem (String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(wham.MOD_ID, name), item);
    }

    public static void registerModItems () {
        wham.LOGGER.info("[Wham!] Things feel different..");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemToItemGroup);
    }
}
