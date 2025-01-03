package com.kckarnige.wham.items;

import com.kckarnige.wham.config.MidnightConfigStuff;
import com.kckarnige.wham.wham;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;


public class ModItems {

    public static final Item MACE_HEAD = registerItem("mace_head", new Item.Settings().rarity(Rarity.RARE));
    public static final Item MACE_TIP = registerItem("mace_tip", new Item.Settings().rarity(Rarity.UNCOMMON));
    public static Item THE_JAR = null;
    public static final FoodComponent THE_JAR_FX = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 2), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 6), 1.0F)
            .alwaysEdible()
            .build();

    private static void addItemToItemGroup(FabricItemGroupEntries entries) {
        entries.add(MACE_HEAD);
        entries.add(MACE_TIP);
    }

    private static Item registerItem (String name, Item.Settings item) {
        return Registry.register(Registries.ITEM, Identifier.of(wham.MOD_ID, name), new Item(item));
    }

    public static void registerModItems () {
        if (MidnightConfigStuff.THE_JAR) {
            THE_JAR = registerItem("the_jar", new Item.Settings().maxCount(69).food(THE_JAR_FX).rarity(Rarity.EPIC));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(THE_JAR));
        }
        wham.LOGGER.info("[Wham!] Spiking up the rattle...");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToItemGroup);
    }
}
