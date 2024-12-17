package com.kckarnige.wham.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.screen.Screen;

import static com.kckarnige.wham.wham.MOD_ID;

public class ModMenuConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, MOD_ID);
    }
}
