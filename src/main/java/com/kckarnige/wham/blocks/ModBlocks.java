package com.kckarnige.wham.blocks;

import com.kckarnige.wham.blocks.NewBlocks.TrapBlock;
import com.kckarnige.wham.wham;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
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

    public static final Block SPIKE_TRAP = registerBlock("spike_trap", new Item.Settings(), "trap", AbstractBlock.Settings.create()
            .strength(0.24f)
            .sounds(BlockSoundGroup.METAL)
            .noCollision()
            .nonOpaque()
            .velocityMultiplier(0.25f)
            .blockVision(Blocks::never)
            .solidBlock(Blocks::never)
            .pistonBehavior(PistonBehavior.DESTROY));


    private static void registerBlockItem (String id, Item.Settings item, Block block) {
        //1.21.2+ block reg sucks
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(wham.MOD_ID, id));
        Registry.register(Registries.ITEM, itemKey, new BlockItem(block, item.useBlockPrefixedTranslationKey().registryKey(itemKey)));
    }

    private static Block registerBlock(String id, Item.Settings itemSettings, String type, AbstractBlock.Settings blockSettings) {
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(wham.MOD_ID, id));
        Block block;
        if (type.equals("trap")) {
            block = new TrapBlock(blockSettings.registryKey(blockKey));
        } else {
            block = new Block(blockSettings.registryKey(blockKey));
        }


        registerBlockItem(id, itemSettings, block);
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static void registerModBlocks() {
        wham.LOGGER.info("Spiking up the floor...");
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(SPIKE_TRAP));
    }
}
