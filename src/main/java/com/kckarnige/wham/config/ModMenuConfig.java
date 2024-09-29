package com.kckarnige.wham.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuConfig implements ModMenuApi {

    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return this::OpenConfig;
    }

    public Screen OpenConfig (Screen parent) {
        return MainConfig.configScreen;
    }
}
