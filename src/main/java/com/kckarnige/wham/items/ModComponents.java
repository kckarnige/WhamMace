package com.kckarnige.wham.items;

import com.kckarnige.wham.wham;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModComponents {
    public static final ComponentType<Boolean> WIND_BOUNCE_READY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(wham.MOD_ID, "wind_bounce_ready"),
            ComponentType.<Boolean>builder().codec(Codec.BOOL).build()
    );

    public static void initialize() {
        wham.LOGGER.info("Harnessing the winds...", wham.MOD_ID);
    }
}