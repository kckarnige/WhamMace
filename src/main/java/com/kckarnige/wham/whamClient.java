package com.kckarnige.wham;

import com.kckarnige.wham.blocks.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import static net.fabricmc.fabric.api.resource.ResourcePackActivationType.*;

public class whamClient implements ClientModInitializer {

	public <string> void registerResourcePack(string Path, ResourcePackActivationType ResourcePackType) {
		ModContainer container = FabricLoader.getInstance().getModContainer(wham.MOD_ID).orElseThrow();
		ResourceManagerHelper.registerBuiltinResourcePack(Identifier.of(wham.MOD_ID, (String) Path), container, ResourcePackType);
	}
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPIKE_TRAP, RenderLayer.getCutout());
		registerResourcePack("perma_wind", NORMAL);
		registerResourcePack("no_wind", NORMAL);
		wham.LOGGER.info("[Wham!] This should look nice...");
	}
}