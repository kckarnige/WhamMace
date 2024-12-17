package com.kckarnige.wham.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class WhamEnchantment {
    public static final RegistryKey<Enchantment> WIND_BOUNCE = of("wind_bounce");

    private static RegistryKey<Enchantment> of(String name) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("wham", name));
    }

    public static void initialize() {
    }
}