package com.kckarnige.wham.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MidnightConfigStuff extends MidnightConfig {
    public static final String SERVER = "server";

    @Comment(category = SERVER, centered = true) public static Comment abilityConfigSet;
    @Entry(category = SERVER) public static boolean AIR_LIFT = true;
    @Entry(category = SERVER) public static boolean AIR_SLAM = true;

    @Comment(category = SERVER) public static Comment ae_spacer;

    @Comment(category = SERVER, centered = true) public static Comment enchantmentConfigSet;
    @Entry(category = SERVER) public static boolean DEFAULT_BOUNCE = false;
    @Entry(category = SERVER) public static boolean LOWER_LEVEL_LIFT = false;
}
