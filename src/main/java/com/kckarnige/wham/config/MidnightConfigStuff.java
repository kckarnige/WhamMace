package com.kckarnige.wham.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MidnightConfigStuff extends MidnightConfig {
    public static final String SERVER = "server";
    public static final String CLIENT = "client";

    @Entry(category = SERVER) public static boolean BIG_BOUNCE = false;
    @Entry(category = SERVER) public static boolean DEFAULT_BOUNCE = false;

    @Entry(category = CLIENT) public static boolean MACE_MODEL = true;
    @Entry(category = CLIENT) public static boolean MACE_WINDU = true;
    @Entry(category = CLIENT) public static boolean MACE_WINDU_4EVS = false;
}
