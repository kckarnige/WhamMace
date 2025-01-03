package com.kckarnige.wham.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MidnightConfigStuff extends MidnightConfig {
    public static final String CONFIG = "CONFIG";

    @Comment(category = CONFIG, centered = true) public static Comment abilityConfigSet;
    @Entry(category = CONFIG) public static boolean AIR_LIFT = true;
    @Entry(category = CONFIG) public static boolean AIR_SLAM = true;

    @Comment(category = CONFIG) public static Comment ae_spacer;

    @Comment(category = CONFIG, centered = true) public static Comment enchantmentConfigSet;
    @Entry(category = CONFIG) public static boolean DEFAULT_BOUNCE = false;
    @Entry(category = CONFIG) public static boolean LOWER_LEVEL_LIFT = false;

    @Comment(category = CONFIG) public static Comment em_spacer;

    @Comment(category = CONFIG, centered = true) public static Comment miscConfigSet;
    @Entry(category = CONFIG) public static boolean REMOVE_RPS = false;
    @Entry(category = CONFIG) public static boolean THE_JAR = false;
}
