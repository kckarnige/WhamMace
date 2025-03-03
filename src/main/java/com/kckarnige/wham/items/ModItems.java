package com.kckarnige.wham.items;

import com.kckarnige.wham.config.MidnightConfigStuff;
import com.kckarnige.wham.wham;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import static net.minecraft.component.type.ConsumableComponents.drink;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;


public class ModItems {

    public static final Item MACE_HEAD = registerItem("mace_head", new Item.Settings().rarity(Rarity.RARE));
    public static final Item MACE_TIP = registerItem("mace_tip", new Item.Settings().rarity(Rarity.UNCOMMON));
    public static Item THE_JAR = null;
    public static final ConsumableComponent THE_JAR_FX = drink()
            .consumeEffect(
                    new ApplyEffectsConsumeEffect(
                            List.of(
                                    new StatusEffectInstance(StatusEffects.REGENERATION, 400, 2),
                                    new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0),
                                    new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 1),
                                    new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 6)
                            )
                    )
            )
            .build();

    private static Item registerItem (String id, Item.Settings item) {
        // This is why plan ahead (don't ask why it took 2 commits to finish this thought)
        // Idk why tf I'm talking, I didn't plan shit 💀
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(wham.MOD_ID, id));
        return Registry.register(Registries.ITEM, key, new Item(item.registryKey(key)));
    }

    public static void registerModItems () {
        if (MidnightConfigStuff.THE_JAR) {
            THE_JAR = registerItem("the_jar", new Item.Settings().maxCount(69).food(new FoodComponent(69,69, true), THE_JAR_FX).rarity(Rarity.EPIC));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> entries.add(THE_JAR));
        }
        wham.LOGGER.info("[Wham!] Spiking up the rattle...");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content ->
                content.addAfter(Items.HEAVY_CORE,
                MACE_TIP,
                MACE_HEAD)
        );
    }
}
