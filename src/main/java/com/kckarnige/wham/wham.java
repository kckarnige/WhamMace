package com.kckarnige.wham;

import com.kckarnige.wham.items.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class wham implements ModInitializer {
	public static final String MOD_ID = "wham";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		LOGGER.info("[Wham!] Now this should pack more of a punch!");
	}
}