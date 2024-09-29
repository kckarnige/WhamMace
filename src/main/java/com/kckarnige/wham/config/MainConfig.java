package com.kckarnige.wham.config;

import net.minecraft.client.MinecraftClient;
import net.treset.vanillaconfig.config.*;
import net.treset.vanillaconfig.config.base.BaseConfig;
import net.treset.vanillaconfig.config.managers.SaveLoadManager;
import net.treset.vanillaconfig.screen.ConfigScreen;

public class MainConfig {
    static PageConfig configPage;
    static PageConfig clientPage;
    static PageConfig serverPage;
    static ConfigScreen configScreen;
    static ConfigScreen clientConfigScreen;
    static ConfigScreen serverConfigScreen;
    public static ButtonConfig CLIENT_CONFIG;
    public static ButtonConfig SERVER_CONFIG;

    public static BooleanConfig MACE_MODEL;
    public static BooleanConfig MACE_WINDU;
    public static BooleanConfig MACE_WINDU_4EVS;
    public static BooleanConfig DEFAULT_BOUNCE;
    public static BooleanConfig BIG_BOUNCE;


    public static void init() {
        configPage = new PageConfig("config.wham");
        clientPage = new PageConfig("config.wham.client");
        serverPage = new PageConfig("config.wham.server");
        CLIENT_CONFIG = new ButtonConfig("config.wham.client_button");
        SERVER_CONFIG = new ButtonConfig("config.wham.server_button");

        MACE_MODEL = new BooleanConfig(true, "config.wham.3d_mace");
        MACE_WINDU = new BooleanConfig(true, "config.wham.mace_wind_toggle");
        MACE_WINDU_4EVS = new BooleanConfig(false, "config.wham.mace_4ever_wind");

        DEFAULT_BOUNCE = new BooleanConfig(false, "config.wham.wind_bounce_default", "config.wham.wind_bounce_default.description");
        BIG_BOUNCE = new BooleanConfig(false, "config.wham.toggle_charge_bounce", "config.wham.toggle_charge_bounce.description");

        configPage.setOptions(new BaseConfig[]{
                CLIENT_CONFIG,
                SERVER_CONFIG
        });
        clientPage.setOptions(new BaseConfig[]{
                MACE_MODEL,
                MACE_WINDU,
                MACE_WINDU_4EVS
        });
        serverPage.setOptions(new BaseConfig[]{
                BIG_BOUNCE,
                DEFAULT_BOUNCE
        });

        clientPage.setSaveName("client");
        clientPage.setPath("wham_config");
        serverPage.setSaveName("server");
        serverPage.setPath("wham_config");

        configScreen = new ConfigScreen(configPage, MinecraftClient.getInstance().currentScreen);
        clientConfigScreen = new ConfigScreen(clientPage, MinecraftClient.getInstance().currentScreen);
        serverConfigScreen = new ConfigScreen(serverPage, MinecraftClient.getInstance().currentScreen);



        SaveLoadManager.globalSaveConfig(clientPage);
        MACE_MODEL.onChange(MainConfig::onBooleanConfigChanged);
        CLIENT_CONFIG.onClickL(MainConfig::onClientButtonClick);
        SERVER_CONFIG.onClickL(MainConfig::onServerButtonClick);

    }

    private static void onClientButtonClick(String s) {
        MinecraftClient.getInstance().setScreen(clientConfigScreen);
    }

    private static void onServerButtonClick(String s) {
        MinecraftClient.getInstance().setScreen(serverConfigScreen);
    }

    static void onBooleanConfigChanged(boolean prevBoolean, String name) {
        if (!MainConfig.MACE_MODEL.getBoolean()) {
            MainConfig.MACE_WINDU_4EVS.setEditable(false);
            MainConfig.MACE_WINDU.setEditable(false);
        } else {
            MainConfig.MACE_WINDU_4EVS.setEditable(true);
            MainConfig.MACE_WINDU.setEditable(true);
        }
    }
}
