package com.kckarnige.wham.blocks;

import com.kckarnige.wham.wham;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SPIKE_TRAP = registerBlock("spike_trap", new Item.Settings(), AbstractBlock.Settings.create()
            .strength(0.1f)
            .sounds(BlockSoundGroup.METAL)
            .noCollision()
            .pistonBehavior(PistonBehavior.DESTROY));


    private static Item registerBlockItem (Item.Settings item, AbstractBlock.Settings blockSettings, RegistryKey<Item> itemKey) {
        //1.21.2+ block reg sucks
        return Registry.register(Registries.ITEM, itemKey, new BlockItem(new Block(blockSettings), item.useBlockPrefixedTranslationKey().registryKey(itemKey)));
    }

    private static Block registerBlock(String id, Item.Settings itemSettings, AbstractBlock.Settings blockSettings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(wham.MOD_ID, id));
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(wham.MOD_ID, id));

        registerBlockItem(itemSettings, blockSettings, itemKey);
        return Registry.register(Registries.BLOCK, blockKey, new Block(blockSettings.registryKey(blockKey)));
    }

    public static void registerModBlocks() {
        wham.LOGGER.info("GOOD MORNING NIGHT CITY!!");
        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> entries.add(SPIKE_TRAP));
    }
}
