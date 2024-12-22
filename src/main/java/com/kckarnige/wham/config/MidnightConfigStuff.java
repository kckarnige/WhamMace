package com.kckarnige.wham.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MidnightConfigStuff extends MidnightConfig {
    public static final String SERVER = "server";

    @Entry(category = SERVER) public static boolean DEFAULT_BOUNCE = false;
}
